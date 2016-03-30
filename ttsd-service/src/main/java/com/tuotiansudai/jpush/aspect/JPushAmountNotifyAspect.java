package com.tuotiansudai.jpush.aspect;

/**
 * Created by gengbeijun on 16/3/29.
 */

import com.tuotiansudai.job.*;
import com.tuotiansudai.repository.mapper.InvestReferrerRewardMapper;
import com.tuotiansudai.repository.mapper.RechargeMapper;
import com.tuotiansudai.repository.mapper.ReferrerRelationMapper;
import com.tuotiansudai.repository.mapper.WithdrawMapper;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.util.JobManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gengbeijun on 16/3/18.
 */
@Component
@Aspect
public class JPushAmountNotifyAspect {
    static Logger logger = Logger.getLogger(JPushAmountNotifyAspect.class);

    public final static String REPAY = "Repay-{0}";
    public final static String RECHARGE = "Recharge-{0}";
    public final static String WITHDRAWAPPLY = "WithDrawApply-{0}";
    public final static String WITHDRAW = "WithDraw-{0}";
    public final static String REFERRERREWARD = "ReferrerReward-{0}";

    @Autowired
    private JobManager jobManager;

    @Autowired
    private ReferrerRelationMapper referrerRelationMapper;

    @Autowired
    private InvestReferrerRewardMapper investReferrerRewardMapper;

    @Autowired
    private RechargeMapper rechargeMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Pointcut("execution(* *..RepayService.postRepayCallback(..))")
    public void postRepayCallbackPointcut() {}

    @Pointcut("execution(* *..RechargeService.rechargeCallback(..))")
    public void rechargeCallbackPointcut() {}

    @Pointcut("execution(* *..WithdrawService.withdrawCallback(..))")
    public void withdrawCallbackPointcut() {}

    @Pointcut("execution(* *..ReferrerRewardService.rewardReferrer(..))")
    public void rewardReferrerPointcut() {}

    @AfterReturning(value = "postRepayCallbackPointcut()", returning = "returnValue")
    public void afterReturningPostRepayCallback(JoinPoint joinPoint, Object returnValue) {
        logger.debug("after repay pointcut");
        try {
            long LoanId = (long)joinPoint.getArgs()[0];
            if((boolean)returnValue){
                createAutoJPushRepayAlertJob(LoanId);
            }
        } catch (Exception e) {
            logger.error("after repay aspect fail ", e);
        }
    }

    @AfterReturning(value = "rechargeCallbackPointcut()")
    public void afterReturningRechargeCallback(JoinPoint joinPoint) {
        logger.debug("after recharge pointcut");
        try {
            Map<String, String> paramsMap = (Map<String, String>) joinPoint.getArgs()[0];
            long orderId = Long.parseLong(paramsMap.get("order_id"));
            RechargeModel  rechargeModel = rechargeMapper.findById(orderId);
            if(rechargeModel != null && RechargeStatus.SUCCESS == rechargeModel.getStatus()){
                createAutoJPushRechargeAlertJob(orderId);
            }
        } catch (Exception e) {
            logger.error("after recharge aspect fail ", e);
        }
    }

    @AfterReturning(value = "withdrawCallbackPointcut()")
    public void afterReturningWithdrawCallback(JoinPoint joinPoint) {
        logger.debug("after Withdraw pointcut");
        try {
            Map<String, String> paramsMap = (Map<String, String>) joinPoint.getArgs()[0];
            long orderId = Long.parseLong(paramsMap.get("order_id"));
            WithdrawModel withdrawModel = withdrawMapper.findById(orderId);
            if(WithdrawStatus.APPLY_SUCCESS == withdrawModel.getStatus())
            {
                createAutoJPushWithDrawApplyAlertJob(orderId);
            }
            else if(WithdrawStatus.SUCCESS == withdrawModel.getStatus())
            {
                createAutoJPushWithDrawAlertJob(orderId);
            }
        } catch (Exception e) {
            logger.error("after Withdraw aspect fail ", e);
        }
    }

    @AfterReturning(value = "rewardReferrerPointcut()")
    public void afterReturningRewardReferrer(JoinPoint joinPoint) {
        logger.debug("after RewardReferrer pointcut");
        try {
            List<InvestModel> successInvestList = (List<InvestModel>) joinPoint.getArgs()[1];
            for (InvestModel invest : successInvestList) {
                List<ReferrerRelationModel> referrerRelationList = referrerRelationMapper.findByLoginName(invest.getLoginName());
                for(ReferrerRelationModel referrerRelationModel : referrerRelationList){
                    InvestReferrerRewardModel investReferrerRewardModel = investReferrerRewardMapper.findByInvestIdAndReferrer(invest.getId(), referrerRelationModel.getReferrerLoginName());
                    createAutoJPushReferrerRewardAlertJob(investReferrerRewardModel.getId());
                }
            }
        } catch (Exception e) {
            logger.error("after RewardReferrer aspect fail ", e);
        }
    }

    private void createAutoJPushRepayAlertJob(long loanId) {
        try {
            Date triggerTime = new DateTime().plusMinutes(AutoJPushRepayAlertJob.JPUSH_ALERT_REPAY_DELAY_MINUTES)
                    .toDate();
            jobManager.newJob(JobType.AutoJPushRepayAlert, AutoJPushRepayAlertJob.class)
                    .addJobData(AutoJPushRepayAlertJob.REPAY_ID_KEY, loanId)
                    .withIdentity(JobType.AutoJPushRepayAlert.name(), formatMessage(REPAY, loanId))
                    .runOnceAt(triggerTime)
                    .submit();
        } catch (SchedulerException e) {
            logger.error("create send AutoJPushRepayAlert job for loan[" + loanId + "] fail", e);
        }
    }

    private void createAutoJPushRechargeAlertJob(long orderId) {
        try {
            logger.debug("prepare job AutoJPushRechargeAlertJob");
            Date triggerTime = new DateTime().plusMinutes(AutoJPushRechargeAlertJob.JPUSH_ALERT_RECHARGE_DELAY_MINUTES)
                    .toDate();

            logger.debug("AutoJPushRechargeAlertJob triggerTime == " + triggerTime);

            jobManager.newJob(JobType.AutoJPushRechargeAlert, AutoJPushRechargeAlertJob.class)
                    .addJobData(AutoJPushRechargeAlertJob.RECHARGE_ID_KEY, orderId)
                    .withIdentity(JobType.AutoJPushRechargeAlert.name(),  formatMessage(RECHARGE, orderId))
                    .runOnceAt(triggerTime)
                    .submit();

            logger.debug("after job AutoJPushRechargeAlertJo b");
        } catch (SchedulerException e) {
            logger.error("create send AutoJPushRepayAlert job for loginName[" + orderId + "] fail", e);
        }
    }

    private void createAutoJPushWithDrawApplyAlertJob(long orderId) {
        try {
            logger.debug("prepare job AutoJPushWithDrawApplyAlertJob");
            Date triggerTime = new DateTime().plusMinutes(AutoJPushWithDrawApplyAlertJob.JPUSH_ALERT_WITHDRAW_APPLY_DELAY_MINUTES)
                    .toDate();
            logger.debug("AutoJPushWithDrawApplyAlertJob triggerTime = " + triggerTime);
            jobManager.newJob(JobType.AutoJPushWithDrawApplyAlert, AutoJPushWithDrawApplyAlertJob.class)
                    .addJobData(AutoJPushWithDrawApplyAlertJob.WITHDRAW_APPLY_ID_KEY, orderId)
                    .withIdentity(JobType.AutoJPushWithDrawApplyAlert.name(), formatMessage(WITHDRAWAPPLY, orderId))
                    .runOnceAt(triggerTime)
                    .submit();
            logger.debug("after job AutoJPushWithDrawApplyAlertJob  ");
        } catch (SchedulerException e) {
            logger.error("create send AutoJPushWithDrawApplyAlert job for orderId[" + orderId + "] fail", e);
        }
    }

    private void createAutoJPushWithDrawAlertJob(long orderId) {
        try {
            logger.debug("prepare job AutoJPushWithDrawAlertJob");
            Date triggerTime = new DateTime().plusMinutes(AutoJPushWithDrawAlertJob.JPUSH_ALERT_WITHDRAW_DELAY_MINUTES)
                    .toDate();
            logger.debug("AutoJPushWithDrawAlertJob triggerTime = " + triggerTime);
            jobManager.newJob(JobType.AutoJPushWithDrawAlert, AutoJPushWithDrawAlertJob.class)
                    .addJobData(AutoJPushWithDrawAlertJob.WITHDRAW_ID_KEY, orderId)
                    .withIdentity(JobType.AutoJPushWithDrawAlert.name(), formatMessage(WITHDRAW, orderId))
                    .runOnceAt(triggerTime)
                    .submit();
        } catch (SchedulerException e) {
            logger.error("create send AutoJPushWithDrawAlert job for orderId[" + orderId + "] fail", e);
        }
    }

    private void createAutoJPushReferrerRewardAlertJob(long orderId) {
        try {
            Date triggerTime = new DateTime().plusMinutes(AutoJPushReferrerRewardAlertJob.JPUSH_ALERT_REFERRER_REWARD_DELAY_MINUTES)
                    .toDate();
            jobManager.newJob(JobType.AutoJPushReferrerRewardAlert, AutoJPushReferrerRewardAlertJob.class)
                    .addJobData(AutoJPushReferrerRewardAlertJob.REFERRER_REWARD_ID_KEY, orderId)
                    .withIdentity(JobType.AutoJPushReferrerRewardAlert.name(), formatMessage(REFERRERREWARD, orderId))
                    .runOnceAt(triggerTime)
                    .submit();
        } catch (SchedulerException e) {
            logger.error("create send AutoJPushReferrerRewardAlert job for orderId[" + orderId + "] fail", e);
        }
    }

    private String formatMessage(String message, Object object){
        return MessageFormat.format(message, object);
    }
}
