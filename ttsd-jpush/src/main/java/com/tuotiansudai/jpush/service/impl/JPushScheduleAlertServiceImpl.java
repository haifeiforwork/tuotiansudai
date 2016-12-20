package com.tuotiansudai.jpush.service.impl;

import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.ScheduleResult;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.schedule.model.TriggerPayload;
import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.jpush.service.JPushScheduleAlertService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class JPushScheduleAlertServiceImpl implements JPushScheduleAlertService {

    static Logger logger = Logger.getLogger(JPushScheduleAlertServiceImpl.class);
    @Value("${common.jpush.masterSecret}")
    private String masterSecret;

    @Value("${common.jpush.appKey}")
    private String appKey;

    private static ScheduleClient scheduleClient = null;

    @Autowired
    private RedisWrapperClient redisClient;

    private static final String APP_PUSH_MSG_ID_KEY = "console:push-msg-ids:";

    private ScheduleClient getScheduleClient(){
        if(scheduleClient == null){
            return new ScheduleClient(masterSecret, appKey, null, ClientConfig.getInstance());
        }
        return scheduleClient;
    }

    @Override
    public ScheduleResult sendJPushScheduleAlert(String jPushAlertId,PushPayload payload,TriggerPayload triggerPayload) {
        if(payload == null || triggerPayload == null){return null;}

        SchedulePayload.Builder schedulePayload = new SchedulePayload.Builder().setName(jPushAlertId)
                .setEnabled(true)
                .setTrigger(triggerPayload).setPush(payload);

        return createSchedule(jPushAlertId,schedulePayload.build(),payload);
    }

    @Override
    public void deletePushScheduleAlert(String jPushAlertId) {
        try {
            getScheduleClient().deleteSchedule(jPushAlertId);
        } catch (APIConnectionException e) {
            logger.error(e.getLocalizedMessage(), e);
        } catch (APIRequestException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public ScheduleResult findPushScheduleAlert(String jPushAlertId) {
        ScheduleResult scheduleResult = null;
        try {
            scheduleResult = getScheduleClient().getSchedule(jPushAlertId);
        } catch (APIConnectionException e) {
            logger.error(e.getLocalizedMessage(), e);
        } catch (APIRequestException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return scheduleResult;
    }

    @Override
    public ScheduleResult updatePushScheduleAlert(String jPushAlertId,SchedulePayload payload){
        ScheduleResult scheduleResult = null;
        try {
            scheduleResult = getScheduleClient().updateSchedule(jPushAlertId,payload);
        } catch (APIConnectionException e) {
            logger.error(e.getLocalizedMessage(), e);
        } catch (APIRequestException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return scheduleResult;
    }

    private ScheduleResult createSchedule(String jPushAlertId,SchedulePayload schedulePayload,PushPayload payload){
        ScheduleResult scheduleResult = null;
        try {
            logger.info(MessageFormat.format("request:{0}:{1} begin", jPushAlertId, payload.toJSON()));
            scheduleResult =  getScheduleClient().createSchedule(schedulePayload);
            logger.info(MessageFormat.format("request:{0}:{1} end", jPushAlertId, scheduleResult.getResponseCode()));
            redisClient.sadd(APP_PUSH_MSG_ID_KEY + jPushAlertId, String.valueOf(scheduleResult.getResponseCode()));
        } catch (APIConnectionException e) {
            logger.error(e.getLocalizedMessage(), e);
        } catch (APIRequestException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return scheduleResult;
    }
}
