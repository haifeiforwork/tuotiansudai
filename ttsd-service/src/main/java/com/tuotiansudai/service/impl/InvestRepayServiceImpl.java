package com.tuotiansudai.service.impl;


import com.tuotiansudai.console.util.LoginUserInfo;
import com.tuotiansudai.coupon.repository.mapper.CouponMapper;
import com.tuotiansudai.coupon.repository.mapper.UserCouponMapper;
import com.tuotiansudai.coupon.repository.model.UserCouponModel;
import com.tuotiansudai.repository.model.LatestInvestView;
import com.tuotiansudai.repository.mapper.InvestRepayMapper;
import com.tuotiansudai.repository.model.InvestRepayModel;
import com.tuotiansudai.service.InvestRepayService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InvestRepayServiceImpl implements InvestRepayService{

    @Autowired
    private InvestRepayMapper investRepayMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public long findByLoginNameAndTimeAndSuccessInvestRepay(String loginName,Date startTime,Date endTime) {
        return investRepayMapper.findByLoginNameAndTimeAndSuccessInvestRepay(loginName, startTime, endTime);
    }

    @Override
    public long findByLoginNameAndTimeAndNotSuccessInvestRepay(String loginName,Date startTime,Date endTime) {
        return investRepayMapper.findByLoginNameAndTimeAndNotSuccessInvestRepay(loginName, startTime, endTime);
    }

    @Override
    public List<InvestRepayModel> findByLoginNameAndTimeSuccessInvestRepayList(String loginName, Date startTime, Date endTime, int startLimit, int endLimit) {
        return investRepayMapper.findByLoginNameAndTimeSuccessInvestRepayList(loginName, startTime, endTime, startLimit, endLimit);
    }

    @Override
    public List<InvestRepayModel> findByLoginNameAndTimeNotSuccessInvestRepayList(String loginName, Date startTime, Date endTime, int startLimit, int endLimit) {
        return investRepayMapper.findByLoginNameAndTimeNotSuccessInvestRepayList(loginName, startTime, endTime, startLimit, endLimit);
    }

    @Override
    public List<LatestInvestView> findLatestInvestByLoginName(String loginName, int startLimit, int endLimit) {
        List<LatestInvestView> latestInvestViews = investRepayMapper.findLatestInvestByLoginName(loginName, startLimit, endLimit);
        for (LatestInvestView latestInvestView : latestInvestViews) {
            List<UserCouponModel> userCouponModels = userCouponMapper.findBirthdaySuccessByLoginNameAndLoanId(LoginUserInfo.getLoginName(), latestInvestView.getLoanId());
            latestInvestView.setBirthdayCoupon(CollectionUtils.isNotEmpty(userCouponModels));
            if (CollectionUtils.isNotEmpty(userCouponModels)) {
                latestInvestView.setBirthdayBenefit(couponMapper.findById(userCouponModels.get(0).getCouponId()).getBirthdayBenefit());
            }
        }
        return latestInvestViews;
    }

    @Override
    public long findSumRepaidInterestByLoginName(String loginName) {
        return investRepayMapper.findSumRepaidInterestByLoginName(loginName);
    }

    @Override
    public long findSumRepayingInterestByLoginName(String loginName) {
        return investRepayMapper.findSumRepayingInterestByLoginName(loginName);
    }

    @Override
    public long findSumRepayingCorpusByLoginName(String loginName) {
        return investRepayMapper.findSumRepayingCorpusByLoginName(loginName);
    }

    @Override
    public long findSumRepaidCorpusByLoginName(String loginName) {
        return investRepayMapper.findSumRepaidCorpusByLoginName(loginName);
    }


}
