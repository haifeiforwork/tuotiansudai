package com.tuotiansudai.scheduler.plugin;

import com.google.common.collect.Lists;
import com.tuotiansudai.job.*;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.TimeZone;

public class JobInitPlugin implements SchedulerPlugin {

    private static Logger logger = Logger.getLogger(JobInitPlugin.class);

    private JobManager jobManager;

    private String schedulerName;

    private final String TIMEZONE_SHANGHAI = "Asia/Shanghai";

    @Value("#{'${activity.weiXin.red.envelop.period}'.split('\\~')}")
    private List<String> weiXinPeriod = Lists.newArrayList();

    public JobInitPlugin(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
        this.schedulerName = scheduler.getSchedulerName().replaceFirst("^Scheduler-", "");
    }

    @Override
    public void start() {
        if (JobType.NormalRepayCallBack.name().equalsIgnoreCase(schedulerName)) {
            createNormalRepayCallBackJobIfNotExist();
        }
        if (JobType.AdvanceRepayCallBack.name().equalsIgnoreCase(schedulerName)) {
            createAdvanceRepayCallBackJobIfNotExist();
        }
        if (JobType.CalculateDefaultInterest.name().equalsIgnoreCase(schedulerName)) {
            createCalculateDefaultInterest();
        }
        if (JobType.AutoReFreshAreaByMobile.name().equalsIgnoreCase(schedulerName)) {
            createRefreshAreaByMobile();
        }
        if (JobType.LoanRepayNotify.name().equalsIgnoreCase(schedulerName)) {
            createLoanRepayNotifyJob();
        }
        if (JobType.BirthdayNotify.name().equalsIgnoreCase(schedulerName)) {
            createBirthdayNotifyJob();
        }
        if (JobType.AutoJPushAlertBirthMonth.name().equalsIgnoreCase(schedulerName)) {
            deleteAutoJPushAlertBirthMonth();
        }
        if (JobType.AutoJPushAlertBirthDay.name().equalsIgnoreCase(schedulerName)) {
            deleteAutoJPushAlertBirthDay();
        }
        if (JobType.AutoJPushNoInvestAlert.name().equalsIgnoreCase(schedulerName)) {
            deleteAutoJPushNoInvestAlert();
        }
        if (JobType.ExperienceRepay.name().equals(schedulerName)) {
            createNewbieExperienceRepayJob();
        }
        if (JobType.CheckUserBalanceMonthly.name().equals(schedulerName)) {
            createCheckUserBalanceJob();
        }
        if (JobType.CouponRepayCallBack.name().equalsIgnoreCase(schedulerName)) {
            createCouponRepayCallBackJobIfNotExist();
        }
        if (JobType.ExtraRateRepayCallBack.name().equalsIgnoreCase(schedulerName)) {
            createExtraRateRepayCallBackIfNotExist();
        }
        if (JobType.PlatformBalanceLowNotify.name().equals(schedulerName)) {
            platformBalanceLowNotifyJob();
        }
        if (JobType.BirthdayMessage.name().equals(schedulerName)) {
            deleteBirthdayMessageSendJob();
        }
        if (JobType.CalculateTravelLuxuryPrize.name().equalsIgnoreCase(schedulerName)) {
            deleteCalculateTravelLuxuryPrizeJob();
        }
        if (JobType.EventMessage.name().equals(schedulerName)) {
            eventMessageJob();
        }
        if (JobType.NormalRepayCallBack.name().equalsIgnoreCase(schedulerName)) {
            deleteNormalRepayCallBackJob();
        }
        if (JobType.AdvanceRepayCallBack.name().equalsIgnoreCase(schedulerName)) {
            deleteAdvanceRepayCallBackJob();
        }
        if (JobType.SendRedEnvelopSplit.name().equalsIgnoreCase(schedulerName)) {
            createRedEnvelopSplitJob();
        }
    }

    @Override
    public void shutdown() {

    }

    private void createNormalRepayCallBackJobIfNotExist() {
        final JobType jobType = JobType.NormalRepayCallBack;
        final String jobGroup = NormalRepayCallbackJob.JOB_GROUP;
        final String jobName = NormalRepayCallbackJob.JOB_NAME;
        try {
            jobManager.newJob(jobType, NormalRepayCallbackJob.class)
                    .replaceExistingJob(true)
                    .runWithSchedule(SimpleScheduleBuilder
                            .repeatSecondlyForever(NormalRepayCallbackJob.RUN_INTERVAL_SECONDS)
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .withIdentity(jobGroup, jobName)
                    .submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createAdvanceRepayCallBackJobIfNotExist() {
        final JobType jobType = JobType.AdvanceRepayCallBack;
        final String jobGroup = AdvanceRepayCallbackJob.JOB_GROUP;
        final String jobName = AdvanceRepayCallbackJob.JOB_NAME;
        try {
            jobManager.newJob(jobType, AdvanceRepayCallbackJob.class)
                    .replaceExistingJob(true)
                    .runWithSchedule(SimpleScheduleBuilder
                            .repeatSecondlyForever(AdvanceRepayCallbackJob.RUN_INTERVAL_SECONDS)
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .withIdentity(jobGroup, jobName)
                    .submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createCalculateDefaultInterest() {
        try {
            jobManager.newJob(JobType.CalculateDefaultInterest, CalculateDefaultInterestJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.CalculateDefaultInterest.name(), JobType.CalculateDefaultInterest.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createRefreshAreaByMobile() {
        try {
            jobManager.newJob(JobType.AutoReFreshAreaByMobile, AutoReFreshAreaByMobileJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.AutoReFreshAreaByMobile.name(), JobType.AutoReFreshAreaByMobile.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void deleteAutoJPushAlertBirthMonth() {
        jobManager.deleteJob(JobType.AutoJPushAlertBirthMonth, JobType.AutoJPushAlertBirthMonth.name(), JobType.AutoJPushAlertBirthMonth.name());
    }

    private void deleteAutoJPushAlertBirthDay() {
        jobManager.deleteJob(JobType.AutoJPushAlertBirthDay, JobType.AutoJPushAlertBirthDay.name(), JobType.AutoJPushAlertBirthDay.name());
    }

    private void deleteAutoJPushNoInvestAlert() {
        jobManager.deleteJob(JobType.AutoJPushNoInvestAlert, JobType.AutoJPushNoInvestAlert.name(), JobType.AutoJPushNoInvestAlert.name());
    }

    private void createLoanRepayNotifyJob() {
        try {
            jobManager.newJob(JobType.LoanRepayNotify, LoanRepayNotifyJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 14 * * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.LoanRepayNotify.name(), JobType.LoanRepayNotify.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createBirthdayNotifyJob() {
        try {
            jobManager.newJob(JobType.BirthdayNotify, BirthdayNotifyJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 12 5 * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.BirthdayNotify.name(), JobType.BirthdayNotify.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createNewbieExperienceRepayJob() {
        try {
            jobManager.newJob(JobType.ExperienceRepay, ExperienceRepayJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 16 * * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.ExperienceRepay.name(), JobType.ExperienceRepay.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createCheckUserBalanceJob() {
        try {
            jobManager.newJob(JobType.CheckUserBalanceMonthly, CheckUserBalanceJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 30 1 ? * 7#1 *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.CheckUserBalanceMonthly.name(), JobType.CheckUserBalanceMonthly.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createCouponRepayCallBackJobIfNotExist() {
        final JobType jobType = JobType.CouponRepayCallBack;
        final String jobGroup = CouponRepayNotifyCallbackJob.JOB_GROUP;
        final String jobName = CouponRepayNotifyCallbackJob.JOB_NAME;
        try {
            jobManager.newJob(jobType, CouponRepayNotifyCallbackJob.class)
                    .replaceExistingJob(true)
                    .runWithSchedule(SimpleScheduleBuilder
                            .repeatSecondlyForever(CouponRepayNotifyCallbackJob.RUN_INTERVAL_SECONDS)
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .withIdentity(jobGroup, jobName)
                    .submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createExtraRateRepayCallBackIfNotExist() {
        final JobType jobType = JobType.ExtraRateRepayCallBack;
        final String jobGroup = ExtraRateInvestCallbackJob.JOB_GROUP;
        final String jobName = ExtraRateInvestCallbackJob.JOB_NAME;
        try {
            jobManager.newJob(jobType, ExtraRateInvestCallbackJob.class)
                    .replaceExistingJob(true)
                    .runWithSchedule(SimpleScheduleBuilder
                            .repeatSecondlyForever(ExtraRateInvestCallbackJob.RUN_INTERVAL_SECONDS)
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .withIdentity(jobGroup, jobName)
                    .submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void platformBalanceLowNotifyJob() {
        try {
            jobManager.newJob(JobType.PlatformBalanceLowNotify, PlatformBalanceMonitorJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 9 * * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.PlatformBalanceLowNotify.name(), JobType.PlatformBalanceLowNotify.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void deleteCalculateTravelLuxuryPrizeJob() {
        jobManager.deleteJob(JobType.CalculateTravelLuxuryPrize, JobType.CalculateTravelLuxuryPrize.name(), JobType.CalculateTravelLuxuryPrize.name());
    }

    private void deleteBirthdayMessageSendJob() {
        jobManager.deleteJob(JobType.BirthdayMessage, JobType.BirthdayMessage.name(), JobType.BirthdayMessage.name());
    }

    private void deleteNormalRepayCallBackJob() {
        jobManager.deleteJob(JobType.NormalRepayCallBack, JobType.NormalRepayCallBack.name(), JobType.NormalRepayCallBack.name());
    }

    private void deleteAdvanceRepayCallBackJob() {
        jobManager.deleteJob(JobType.AdvanceRepayCallBack, JobType.AdvanceRepayCallBack.name(), JobType.AdvanceRepayCallBack.name());
    }

    private void eventMessageJob() {
        try {
            jobManager.newJob(JobType.EventMessage, EventMessageJob.class).replaceExistingJob(true)
                    .runWithSchedule(CronScheduleBuilder.cronSchedule("0 0 10 * * ? *").inTimeZone(TimeZone.getTimeZone(TIMEZONE_SHANGHAI)))
                    .withIdentity(JobType.EventMessage.name(), JobType.EventMessage.name()).submit();
        } catch (SchedulerException e) {
            logger.info(e.getLocalizedMessage(), e);
        }
    }

    private void createRedEnvelopSplitJob() {
        try {
            jobManager.newJob(JobType.SendRedEnvelopSplit, AssignRedEnvelopSplitJob.class)
                    .withIdentity(JobType.SendRedEnvelopSplit.name(), JobType.SendRedEnvelopSplit.name())
                    .replaceExistingJob(false)
                    .runOnceAt(DateTime.parse(weiXinPeriod.get(1), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate()).submit();
        } catch (SchedulerException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }
}
