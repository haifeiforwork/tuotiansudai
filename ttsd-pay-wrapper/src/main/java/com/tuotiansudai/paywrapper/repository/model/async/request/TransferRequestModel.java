package com.tuotiansudai.paywrapper.repository.model.async.request;

import cn.jpush.api.utils.StringUtils;
import com.tuotiansudai.enums.AsyncUmPayService;
import com.tuotiansudai.paywrapper.repository.model.*;
import com.tuotiansudai.repository.model.Source;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TransferRequestModel extends BaseAsyncRequestModel {

    private String orderId;

    private String merDate;

    private String merAccountId;

    private String particAccType = UmPayParticAccType.INDIVIDUAL.getCode();

    private String transAction = UmPayTransAction.OUT.getCode();

    private String particUserId;

    private String particAccountId;

    private String amount;

    public TransferRequestModel() {
    }

    private TransferRequestModel(String orderId, String payUserId, String particAccountId, String amount, AsyncUmPayService asyncUmPayService) {
        super(Source.WEB, asyncUmPayService);
        this.service = asyncUmPayService.getServiceName();
        this.orderId = orderId;
        this.particUserId = payUserId;
        this.particAccountId = particAccountId;
        this.amount = amount;
        this.merDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        this.particAccType = UmPayParticAccType.INDIVIDUAL.getCode();
        this.transAction = UmPayTransAction.OUT.getCode();
    }

    public static TransferRequestModel newLotteryReward(String orderId, String payUserId, String particAccountId, String amount) {
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.LOTTERY_REWARD_TRANSFER);
    }

    public static TransferRequestModel newReferrerRewardTransferRequest(String orderId, String payUserId, String particAccountId, String amount) {
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.REFERRER_REWARD_TRANSFER);
    }

    public static TransferRequestModel newRedEnvelopeCouponRequest(String orderId, String payUserId, String particAccountId, String amount) {
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.RED_ENVELOPE_TRANSFER);
    }

    public static TransferRequestModel newCouponRepayRequest(String orderId, String payUserId, String particAccountId, String amount) {
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.COUPON_REPAY_TRANSFER);
    }

    public static TransferRequestModel newExtraRateRequest(String orderId, String payUserId, String particAccountId, String amount) {
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.EXTRA_RATE_TRANSFER);
    }

    public static TransferRequestModel experienceInterestRequest(String orderId, String payUserId, String particAccountId, String amount) {
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.EXPERIENCE_INTEREST_TRANSFER);
    }

    public static TransferRequestModel newPayrollRequest(String orderId, String payUserId, String particAccountId, String amount){
        return new TransferRequestModel(orderId, payUserId, particAccountId, amount, AsyncUmPayService.PAYROLL_TRANSFER);
    }

    @Override
    public Map<String, String> generatePayRequestData() {
        Map<String, String> payRequestData = super.generatePayRequestData();
        payRequestData.put("partic_acc_type", this.particAccType);
        payRequestData.put("trans_action", this.transAction);
        payRequestData.put("order_id", this.orderId);
        payRequestData.put("partic_user_id", this.particUserId);
        payRequestData.put("amount", this.amount);
        payRequestData.put("mer_date", this.merDate);
        if (StringUtils.isNotEmpty(this.notifyUrl)) {
            payRequestData.put("notify_url", this.notifyUrl);
        }
        return payRequestData;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getParticAccType() {
        return particAccType;
    }

    public void setParticAccType(String particAccType) {
        this.particAccType = particAccType;
    }

    public String getTransAction() {
        return transAction;
    }

    public void setTransAction(String transAction) {
        this.transAction = transAction;
    }

    public String getMerDate() {
        return merDate;
    }

    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }

    public String getMerAccountId() {
        return merAccountId;
    }

    public void setMerAccountId(String merAccountId) {
        this.merAccountId = merAccountId;
    }

    public String getParticUserId() {
        return particUserId;
    }

    public void setParticUserId(String particUserId) {
        this.particUserId = particUserId;
    }

    public String getParticAccountId() {
        return particAccountId;
    }

    public void setParticAccountId(String particAccountId) {
        this.particAccountId = particAccountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
