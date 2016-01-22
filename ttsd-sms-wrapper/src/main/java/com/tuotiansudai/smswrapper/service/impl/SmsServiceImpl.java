package com.tuotiansudai.smswrapper.service.impl;


import com.google.common.collect.ImmutableMap;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.InvestSmsNotifyDto;
import com.tuotiansudai.dto.SmsCouponNotifyDto;
import com.tuotiansudai.dto.SmsDataDto;
import com.tuotiansudai.dto.SmsFatalNotifyDto;
import com.tuotiansudai.repository.model.CouponType;
import com.tuotiansudai.repository.model.Environment;
import com.tuotiansudai.smswrapper.SmsTemplate;
import com.tuotiansudai.smswrapper.client.SmsClient;
import com.tuotiansudai.smswrapper.repository.mapper.*;
import com.tuotiansudai.smswrapper.service.SmsService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsClient smsClient;

    @Value("#{'${sms.fatal.mobile}'.split('\\|')}")
    private List<String> fatalNotifyMobiles;

    @Value("${common.environment}")
    private Environment environment;

    @Override
    public BaseDto<SmsDataDto> sendRegisterCaptcha(String mobile, String captcha, String ip) {
        Map<String, String> map = ImmutableMap.<String, String>builder().put("captcha", captcha).build();
        String content = SmsTemplate.SMS_REGISTER_CAPTCHA_TEMPLATE.generateContent(map);
        return smsClient.sendSMS(RegisterCaptchaMapper.class, mobile, content, ip);
    }

    @Override
    public BaseDto<SmsDataDto> sendInvestNotify(InvestSmsNotifyDto dto) {
        Map<String, String> map = ImmutableMap.<String, String>builder()
                .put("loanName", dto.getLoanName())
                .put("amount", dto.getAmount())
                .build();
        String content = SmsTemplate.SMS_INVEST_NOTIFY_TEMPLATE.generateContent(map);
        return smsClient.sendSMS(InvestNotifyMapper.class, dto.getMobile(), content, "");
    }

    @Override
    public BaseDto<SmsDataDto> sendRetrievePasswordCaptcha(String mobile, String captcha, String ip) {
        Map<String, String> map = ImmutableMap.<String, String>builder().put("captcha", captcha).build();
        String content = SmsTemplate.SMS_MOBILE_CAPTCHA_TEMPLATE.generateContent(map);
        return smsClient.sendSMS(RetrievePasswordCaptchaMapper.class, mobile, content, ip);
    }

    @Override
    public BaseDto<SmsDataDto> sendPasswordChangedNotify(String mobile) {
        String content = SmsTemplate.SMS_PASSWORD_CHANGED_NOTIFY_TEMPLATE.generateContent(new HashMap<String,String>(0));
        return smsClient.sendSMS(UserPasswordChangedNotifyMapper.class, mobile, content, "");
    }

    @Override
    public BaseDto<SmsDataDto> sendFatalNotify(SmsFatalNotifyDto notify) {
        BaseDto<SmsDataDto> result = new BaseDto<>();
        SmsDataDto dataDto = new SmsDataDto();
        result.setData(dataDto);
        dataDto.setStatus(true);

        for (String mobile : fatalNotifyMobiles) {
            Map<String, String> map = ImmutableMap.<String, String>builder()
                    .put("env", environment.name())
                    .put("errorMessage", notify.getErrorMessage())
                    .build();
            String content = SmsTemplate.SMS_FATAL_NOTIFY_TEMPLATE.generateContent(map);
            BaseDto<SmsDataDto> dto = smsClient.sendSMS(FatalNotifyMapper.class, mobile, content, "");
            if (!dto.getData().getStatus()) {
                result = dto;
            }
        }
        return result;
    }

    @Override
    public BaseDto<SmsDataDto> couponNotify(SmsCouponNotifyDto notifyDto) {
        Map<String, String> map = ImmutableMap.<String, String>builder()
                .put("coupon", (notifyDto.getCouponType() == CouponType.INTEREST_COUPON ? MessageFormat.format("+{0}%", notifyDto.getRate()) : MessageFormat.format("{0}元", notifyDto.getAmount())) + notifyDto.getCouponType().getName())
                .put("expiredDate", notifyDto.getExpiredDate())
                .build();
        String content = SmsTemplate.SMS_COUPON_NOTIFY_TEMPLATE.generateContent(map);
        return smsClient.sendSMS(CouponNotifyMapper.class, notifyDto.getMobile(), content, "");
    }
}