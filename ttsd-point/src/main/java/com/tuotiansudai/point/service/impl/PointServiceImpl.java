package com.tuotiansudai.point.service.impl;


import com.google.common.base.Strings;
import com.tuotiansudai.coupon.dto.ExchangeCouponDto;
import com.tuotiansudai.coupon.repository.mapper.CouponMapper;
import com.tuotiansudai.coupon.repository.model.CouponModel;
import com.tuotiansudai.point.repository.model.PointBusinessType;
import com.tuotiansudai.point.service.PointBillService;
import com.tuotiansudai.point.service.PointService;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.LoanMapper;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.util.InterestCalculator;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

@Service
public class PointServiceImpl implements PointService {
    static Logger logger = Logger.getLogger(PointServiceImpl.class);

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private PointBillService pointBillService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private UserMapper userMapper;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.national.startTime}\")}")
    private Date activityNationalStartTime;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.national.endTime}\")}")
    private Date activityNationalEndTime;

    @Override
    @Transactional
    public void createCouponAndExchange(String loginName, ExchangeCouponDto exchangeCouponDto) {
        CouponModel couponModel = new CouponModel(exchangeCouponDto);
        couponModel.setCreatedBy(loginName);
        couponModel.setCreatedTime(new Date());
        couponMapper.create(couponModel);
    }

    @Override
    @Transactional
    public void obtainPointInvest(InvestModel investModel) {
        LoanModel loanModel = loanMapper.findById(investModel.getLoanId());
        int duration = loanModel.getDuration();
        long point = new BigDecimal((investModel.getAmount()*duration/InterestCalculator.DAYS_OF_YEAR)).divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN).longValue();
        PointBusinessType pointBusinessType = PointBusinessType.INVEST;
        Date nowDate = DateTime.now().toDate();
        if(nowDate.before(activityNationalEndTime) && nowDate.after(activityNationalStartTime) && loanModel.getActivityType().equals(ActivityType.ACTIVITY)){
            pointBusinessType = PointBusinessType.ACTIVITY;
            UserModel userModel = userMapper.findByLoginName(investModel.getLoginName());
            if(userModel.getRegisterTime().before(activityNationalEndTime) && userModel.getRegisterTime().after(activityNationalStartTime) && !Strings.isNullOrEmpty(userModel.getReferrer())){
                pointBillService.createPointBill(userModel.getReferrer(), investModel.getId(), pointBusinessType, (long) (point * 0.1));
            }
            
            point *= 2;
        }
        pointBillService.createPointBill(investModel.getLoginName(), investModel.getId(), pointBusinessType, point);
        logger.debug(MessageFormat.format("{0} has obtained point {1}", investModel.getId(), point));
    }

    @Override
    public long getAvailablePoint(String loginName) {
        AccountModel accountModel = accountMapper.findByLoginName(loginName);
        return accountModel != null ? accountModel.getPoint() : 0;
    }
}
