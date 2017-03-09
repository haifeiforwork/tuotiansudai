package com.tuotiansudai.scheduler.user;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.tuotiansudai.client.MQWrapperClient;
import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.UserModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ReferrerRewardScheduler {

    private static Logger logger = LoggerFactory.getLogger(ReferrerRewardScheduler.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MQWrapperClient mqClient;

    @Autowired
    private InvestMapper investMapper;

    private Date startTime = DateTime.parse("2017-03-07 00:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

    @Scheduled(cron = "0 0 0/3 1/1 * ?", zone = "Asia/Shanghai")
    public void referrerReward() {
        logger.info("[ReferrerRewardScheduler] is start ...");
        DateTime nowTime = DateTime.now();
        Date endTime = nowTime.withTimeAtStartOfDay().toDate();
        Date registerStartTime = nowTime.plusMonths(-1).plusDays(-15).toDate();
        if(registerStartTime.before(startTime)){
            registerStartTime = startTime;
        }
        List<UserModel> registerUsers = userMapper.findUsersByRegisterTimeOrReferrer(registerStartTime, endTime, null);
        Map<String, Integer> referrerMaps = Maps.newConcurrentMap();
        registerUsers.stream()
                .filter(userModel -> !Strings.isNullOrEmpty(userModel.getReferrer()))
                .filter(userModel -> investMapper.sumSuccessActivityInvestAmount(userModel.getLoginName(), null, userModel.getRegisterTime(), new DateTime(userModel.getRegisterTime()).plusDays(15).toDate()) > 200l)
                .forEach(userModel -> {
                    if (referrerMaps.get(userModel.getReferrer()) == null) {
                        referrerMaps.put(userModel.getReferrer(), 1);
                    } else {
                        referrerMaps.put(userModel.getReferrer(), referrerMaps.get(userModel.getReferrer()) + 1);
                    }
                });

        referrerMaps.forEach((k, count) -> {
            if (2 <= count && count <= 4) {
                couponAssign(k, 403l);
            } else if (5 <= count && count <= 8) {
                couponAssign(k, 404l);
            } else if (9 <= count && count <= 10) {
                couponAssign(k, 405l);
                couponAssign(k, 406l);
            } else if (10 < count) {
                couponAssign(k, 407l);
                couponAssign(k, 408l);
            }
        });

        logger.info("[ReferrerRewardScheduler] is done");
    }

    private void couponAssign(String loginName, long couponId){
        logger.info(MessageFormat.format("[ReferrerRewardScheduler] assign coupon. loginName:{0}, couponId:{1}", loginName, String.valueOf(couponId)));
        mqClient.sendMessage(MessageQueue.CouponAssigning, loginName + ":" + couponId);
    }

}
