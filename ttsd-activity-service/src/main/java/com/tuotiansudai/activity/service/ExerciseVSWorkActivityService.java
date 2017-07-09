package com.tuotiansudai.activity.service;

import com.tuotiansudai.activity.repository.dto.ExchangePrizeDto;
import com.tuotiansudai.activity.repository.mapper.UserExchangePrizeMapper;
import com.tuotiansudai.activity.repository.model.ActivityCategory;
import com.tuotiansudai.activity.repository.model.ExchangePrize;
import com.tuotiansudai.activity.repository.model.UserExchangePrizeModel;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.AccountModel;
import com.tuotiansudai.repository.model.InvestModel;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.util.AmountConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
@Service
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
    private UserExchangePrizeMapper userexchangePrizeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private LotteryDrawActivityService lotteryDrawActivityService;

    public int drawTimeByLoginNameAndActivityCategory(String mobile){
        if(new Date().before(ActivityStartTime) || new Date().after(ActivityEndTime)){
            return 0;
        }
        UserModel userModel=userMapper.findByMobile(mobile);
        return lotteryDrawActivityService.getExerciseVSWorkDrawTime(userModel,ActivityCategory.EXERCISE_WORK_ACTIVITY)<=0?0:lotteryDrawActivityService.getExerciseVSWorkDrawTime(userModel,ActivityCategory.EXERCISE_WORK_ACTIVITY);

    }

    public long sumInvestByLoginNameExceptTransferAndTime(String loginName){
        return investMapper.findSuccessByLoginNameExceptTransferAndTime(loginName,ActivityStartTime,ActivityEndTime).stream().mapToLong(i->i.getAmount()).sum();
    }

    public ExchangePrize getPrizeByMobile(String mobile){
        return userexchangePrizeMapper.findUserExchangePrizeByMobile(mobile,ActivityCategory.EXERCISE_WORK_ACTIVITY)==null?null:
                userexchangePrizeMapper.findUserExchangePrizeByMobile(mobile,ActivityCategory.EXERCISE_WORK_ACTIVITY)==null?null:userexchangePrizeMapper.findUserExchangePrizeByMobile(mobile,ActivityCategory.EXERCISE_WORK_ACTIVITY).getPrize();
    }

    @Transactional
    public ExchangePrizeDto exchangePrize(ExchangePrize exchangePrize, String mobile, ActivityCategory activityCategory){
        if (StringUtils.isEmpty(mobile)){
           return new ExchangePrizeDto(4); //还未登录
        }

        Date nowDate=DateTime.now().toDate();
        if (nowDate.before(ActivityStartTime) || nowDate.after(ActivityEndTime)) {
            return new ExchangePrizeDto(3);//不在活动时间范围内！
        }

        UserModel userModel= userMapper.findByMobile(mobile);
        userMapper.lockByLoginName(userModel.getLoginName());

        UserExchangePrizeModel userExchangePrizeModel=userexchangePrizeMapper.findUserExchangePrizeByMobile(mobile,activityCategory);

        if(userExchangePrizeModel!=null && userExchangePrizeModel.getPrize().getExchangeMoney()==exchangePrize.getExchangeMoney()){
            return new ExchangePrizeDto(5);//已选择同档奖品，不可更改
        }else if(userExchangePrizeModel!=null && userExchangePrizeModel.getPrize().getExchangeMoney()>exchangePrize.getExchangeMoney()){
            return new ExchangePrizeDto(6);//已选择奖品
        }

        long amount=0;
        List<InvestModel> investModels=investMapper.findSuccessByLoginNameExceptTransferAndTime(userModel.getLoginName(),ActivityStartTime,ActivityEndTime);
        for (InvestModel investModel:investModels) {
            amount+=investModel.getAmount();
        }
        if (exchangePrize.getExchangeMoney()>amount){
            return new ExchangePrizeDto(1,null,null,AmountConverter.convertCentToString(exchangePrize.getExchangeMoney()-amount));//钱不够
        }


        AccountModel accountModel = accountMapper.findByLoginName(userModel.getLoginName());
        try {
            if (userExchangePrizeModel==null){
                userexchangePrizeMapper.create(new UserExchangePrizeModel(mobile,userModel.getLoginName(),accountModel != null ? userModel.getUserName() : "",exchangePrize,DateTime.now().toDate(),activityCategory));
            }else{
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
