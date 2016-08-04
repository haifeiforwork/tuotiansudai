package com.tuotiansudai.util;

import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.Source;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

@Service
public class RandomUtils {

    private final static String REDIS_KEY_TEMPLATE = "webmobile:{0}:{1}:showinvestorname";

    private static final String numberChar = "0123456789";

    @Value("#{'${web.random.investor.list}'.split('\\|')}")
    private List<String> showRandomLoginNameList;

    @Autowired
    private RedisWrapperClient redisWrapperClient;

    @Autowired
    private UserMapper userMapper;

    private String generateNumString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return stringBuilder.toString();
    }

    private static String showChar(int showLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < showLength; i++) {
            sb.append('*');
        }
        return sb.toString();
    }

    public String encryptMobile(String loginName, String investorLoginName, long investId, Source source) {
        String userMobile;
        String investUserMobile = userMapper.findByLoginName(investorLoginName).getMobile();
        if (StringUtils.isNotEmpty(loginName)) {
            userMobile = userMapper.findByLoginName(loginName).getMobile();
            if (investUserMobile.equalsIgnoreCase(userMobile)) {
                return investUserMobile;
            }
        }
        String redisKey = MessageFormat.format(REDIS_KEY_TEMPLATE, String.valueOf(investId), investUserMobile);
        if (showRandomLoginNameList.contains(investorLoginName) && !redisWrapperClient.exists(redisKey)) {
            redisWrapperClient.set(redisKey, investUserMobile.substring(0, 3) + RandomUtils.showChar(4) + generateNumString(4));
        }
        String encryptMobile;
        if (source.equals(Source.WEB)) {
            encryptMobile = encryptWebMiddleMobile(investUserMobile);
        } else {
            encryptMobile = encryptAppMiddleMobile(investUserMobile);
        }
        return redisWrapperClient.exists(redisKey) ? redisWrapperClient.get(redisKey) : encryptMobile;
    }

    public String encryptMobile(String loginName, String encryptLoginName) {
        if (encryptLoginName.equalsIgnoreCase(loginName)) {
            return "您的位置";
        }

        return encryptAppMiddleMobile(userMapper.findByLoginName(encryptLoginName).getMobile());
    }

    public String encryptMobile(String loginName, String encryptLoginName, Source source) {
        if (encryptLoginName.equalsIgnoreCase(loginName)) {
            return userMapper.findByLoginName(loginName).getMobile();
        }

        if (source.equals(Source.WEB)) {
            return encryptWebMiddleMobile(userMapper.findByLoginName(encryptLoginName).getMobile());
        }
        return encryptAppMiddleMobile(userMapper.findByLoginName(encryptLoginName).getMobile());
    }

    public String encryptWebMiddleMobile(String mobile) {
        return mobile.substring(0, 3) + RandomUtils.showChar(4) + mobile.substring(7);
    }

    public String encryptAppMiddleMobile(String mobile) {
        return mobile.substring(0, 3) + RandomUtils.showChar(2) + mobile.substring(9);
    }

}
