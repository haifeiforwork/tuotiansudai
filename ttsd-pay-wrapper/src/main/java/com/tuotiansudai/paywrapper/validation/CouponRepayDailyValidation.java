package com.tuotiansudai.paywrapper.validation;

import com.tuotiansudai.enums.UserBillBusinessType;
import com.tuotiansudai.paywrapper.repository.mapper.DailyValidationMapper;
import com.tuotiansudai.paywrapper.service.UMPayRealTimeStatusService;
import com.tuotiansudai.repository.mapper.UserBillMapper;
import com.tuotiansudai.repository.model.UserBillModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;


@Component
public class CouponRepayDailyValidation extends BaseDailyValidation implements DailyValidation {

    private final static Logger logger = Logger.getLogger(CouponRepayDailyValidation.class);

    @Autowired
    public CouponRepayDailyValidation(DailyValidationMapper dailyValidationMapper, UMPayRealTimeStatusService umPayRealTimeStatusService, UserBillMapper userBillMapper) {
        this.dailyValidationMapper = dailyValidationMapper;
        this.umPayRealTimeStatusService = umPayRealTimeStatusService;
        this.userBillMapper = userBillMapper;
    }

    public ValidationReport validate() {
        logger.info("[Coupon Repay Daily Validation] starting...");

        List<Map<String, String>> transactions = this.dailyValidationMapper.findCouponRepayTransactions();

        logger.info(MessageFormat.format("[Coupon Repay Daily Validation] sum is {0}", transactions.size()));

        ValidationReport validationReport = this.generateReport("04", transactions);
        validationReport.setTitle("优惠券还款业务统计");
        validationReport.setMustacheContext("couponRepay");

        return validationReport;
    }

    @Override
    protected boolean checkUserBill(String orderId, String amount) {
        long businessId = Long.parseLong(orderId.split("X")[0]);
        UserBillModel couponRepayUserBillModel = userBillMapper.findByOrderIdAndBusinessType(businessId, UserBillBusinessType.INTEREST_COUPON);
        UserBillModel investFeeRepayUserBillModel = userBillMapper.findByOrderIdAndBusinessType(businessId, UserBillBusinessType.INVEST_FEE);
        return couponRepayUserBillModel != null
                && investFeeRepayUserBillModel != null
                && couponRepayUserBillModel.getAmount() - investFeeRepayUserBillModel.getAmount() == Long.parseLong(amount);
    }
}
