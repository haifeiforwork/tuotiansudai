package com.tuotiansudai.activity.service;

import com.google.common.base.Strings;
import com.tuotiansudai.activity.repository.dto.ExchangePrizeDto;
import com.tuotiansudai.activity.repository.mapper.UserExchangePrizeMapper;
import com.tuotiansudai.activity.repository.mapper.UserLotteryPrizeMapper;
import com.tuotiansudai.activity.repository.model.ActivityCategory;
import com.tuotiansudai.activity.repository.model.ExchangePrize;
import com.tuotiansudai.activity.repository.model.UserExchangePrizeModel;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.InvestModel;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.util.AmountConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExerciseVSWorkActivityService {

    static Logger logger = Logger.getLogger(ExerciseVSWorkActivityService.class);

    private final long EACH_INVEST_AMOUNT_100000 = 1000000L;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.exercise.work.startTime}\")}")
    private Date ActivityStartTime;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.exercise.work.endTime}\")}")
    private Date ActivityEndTime;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private UserLotteryPrizeMapper userLotteryPrizeMapper;

    @Autowired
    private UserExchangePrizeMapper userexchangePrizeMapper;

    @Autowired
    private UserMapper userMapper;

    public int drawTimeByLoginNameAndActivityCategory(String mobile,String loginName){
        if(Strings.isNullOrEmpty(loginName)){
            return 0;
        }
        int time=0;
        int sumEveryDayDraw=0;

        DateTime startTime=DateTime.parse(new SimpleDateFormat().format(ActivityStartTime),DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        for(startTime.toDate();startTime.toDate().before(DateTime.now().withTimeAtStartOfDay().plusDays(1).toDate());startTime.plusDays(1).toDate()){
            sumEveryDayDraw+=userLotteryPrizeMapper.findUserLotteryPrizeCountViews(mobile, null, ActivityCategory.EXERCISE_WORK_ACTIVITY,
                    startTime.toDate() , startTime.plusDays(1).plusMillis(-1).toDate()) == 0 ? 0 : 1;
        }

        List<InvestModel> investModels=investMapper.findSuccessByLoginNameExceptTransferAndTime(loginName,ActivityStartTime,ActivityEndTime);
        for (InvestModel investModel:investModels) {
            time+=investModel.getAmount()<EACH_INVEST_AMOUNT_100000?0:(int)(investModel.getAmount()/EACH_INVEST_AMOUNT_100000);
        }

        return time+sumEveryDayDraw > 0 ? time+sumEveryDayDraw - userLotteryPrizeMapper.findUserLotteryPrizeCountViews(mobile, null, ActivityCategory.EXERCISE_WORK_ACTIVITY,ActivityStartTime,ActivityEndTime) : time+sumEveryDayDraw;

    }

    public String sumInvestByLoginNameExceptTransferAndTime(String loginName){
        if(Strings.isNullOrEmpty(loginName)){
            return "0";
        }

        List<InvestModel> investModels=investMapper.findSuccessByLoginNameExceptTransferAndTime(loginName,ActivityStartTime,ActivityEndTime);
        long amount=0;
        for (InvestModel investModel:investModels) {
            amount+=investModel.getAmount();
        }
        return AmountConverter.convertCentToString(amount);
    }

    public String getExchangePrizeByMobile(String mobile,String loginName){
        if(Strings.isNullOrEmpty(loginName)){
            return "0";
        }
        List<UserExchangePrizeModel> userExchangePrizeModels=userexchangePrizeMapper.findUserExchangePrizeByMobile(mobile,ActivityCategory.EXERCISE_WORK_ACTIVITY);
        if (userExchangePrizeModels.size()==0){
            return "0";
        }
        return userExchangePrizeModels.get(0).getPrize().getPrizeName();
    }

    public ExchangePrizeDto exchangePrize(ExchangePrize exchangePrize, String mobile, ActivityCategory activityCategory){
       if (StringUtils.isEmpty(mobile)){
           return new ExchangePrizeDto(4); //还未登录
       }

        UserModel userModel= userMapper.findByMobile(mobile);
        if (userModel==null){
            return new ExchangePrizeDto(2);//用户不存在
        }

        Date nowDate=DateTime.now().toDate();
        if (!nowDate.before(ActivityStartTime) || !nowDate.after(ActivityEndTime)) {
            return new ExchangePrizeDto(3);//不在活动时间范围内！
        }

        long exchangeMoney=exchangePrize.getExchangeMoney();
        List<InvestModel> investModels=investMapper.findSuccessByLoginNameExceptTransferAndTime(mobile,ActivityStartTime,ActivityEndTime);
        long amount=0;
        for (InvestModel investModel:investModels) {
            amount+=investModel.getAmount();
        }
        if (exchangeMoney<amount){
            return new ExchangePrizeDto(1,null,null,amount-exchangeMoney);//钱不够
        }

        List<UserExchangePrizeModel> userExchangePrizeModels=userexchangePrizeMapper.findUserExchangePrizeByMobile(mobile,activityCategory);
        try {
            if (userExchangePrizeModels.size()==0){
                userexchangePrizeMapper.create(new UserExchangePrizeModel(mobile,userModel.getLoginName(),userModel.getUserName(),amount,exchangePrize,DateTime.now().toDate(),activityCategory));
            }else{
                UserExchangePrizeModel userExchangePrizeModel=userExchangePrizeModels.get(0);
                userExchangePrizeModel.setInvestAmount(amount);
                userExchangePrizeModel.setPrize(exchangePrize);
                userExchangePrizeModel.setExchangeTime(DateTime.now().toDate());
                userexchangePrizeMapper.updatePrize(userExchangePrizeModel);
            }
        }catch (Exception e){
            logger.error(MessageFormat.format("exchange is fail, mobile:{0},activity:{1}", mobile, activityCategory.getDescription()));
        }

        return new ExchangePrizeDto(0,exchangePrize.name(),exchangePrize.getPrizeName());//成功

    }


}
