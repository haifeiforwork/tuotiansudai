package com.tuotiansudai.paywrapper.service.impl;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.CreditLoanRechargeDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.enums.UserBillBusinessType;
import com.tuotiansudai.exception.AmountTransferException;
import com.tuotiansudai.paywrapper.client.PayAsyncClient;
import com.tuotiansudai.paywrapper.client.PaySyncClient;
import com.tuotiansudai.paywrapper.exception.PayException;
import com.tuotiansudai.paywrapper.repository.mapper.CreditLoanRechargePwdMapper;
import com.tuotiansudai.paywrapper.repository.mapper.CreditLoanRechargeNopwdMapper;
import com.tuotiansudai.paywrapper.repository.mapper.TransferNotifyMapper;
import com.tuotiansudai.paywrapper.repository.model.async.callback.BaseCallbackRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.callback.TransferNotifyRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.request.ProjectTransferNopwdRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.request.ProjectTransferRequestModel;
import com.tuotiansudai.paywrapper.repository.model.sync.response.ProjectTransferNopwdResponseModel;
import com.tuotiansudai.paywrapper.service.CreditLoanBillService;
import com.tuotiansudai.paywrapper.service.CreditLoanRechargeService;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.CreditLoanRechargeMapper;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.util.AmountTransfer;
import com.tuotiansudai.util.IdGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CreditLoanRechargeServiceImpl implements CreditLoanRechargeService {

    static Logger logger = Logger.getLogger(CreditLoanRechargeServiceImpl.class);
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private PayAsyncClient payAsyncClient;
    @Autowired
    private PaySyncClient paySyncClient;
    @Autowired
    private AmountTransfer amountTransfer;
    @Autowired
    private CreditLoanBillService creditLoanBillService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CreditLoanRechargeMapper creditLoanRechargeMapper;

    @Override
    @Transactional
    public BaseDto<PayDataDto> creditLoanRechargeNoPwd(CreditLoanRechargeDto creditLoanRechargeDto) {
        BaseDto<PayDataDto> baseDto = new BaseDto<>();
        PayDataDto payDataDto = new PayDataDto();
        baseDto.setData(payDataDto);

        UserModel userModel = userMapper.findByMobile(creditLoanRechargeDto.getMobile());

        CreditLoanRechargeModel model = new CreditLoanRechargeModel(creditLoanRechargeDto, userModel.getLoginName());
        model.setId(IdGenerator.generate());

        AccountModel accountModel = accountMapper.findByLoginName(model.getLoginName());

        String remark = MessageFormat.format("{0} 从 {1} 账户为信用贷标的账户充值 {2} 元", creditLoanRechargeDto.getOperatorLoginName(),
                userModel.getMobile(), creditLoanRechargeDto.getAmount());
        model.setRemark(remark);

        ProjectTransferNopwdRequestModel requestModel = ProjectTransferNopwdRequestModel.newCreditLoanPurchaseNopwdRequest(null,
                String.valueOf(IdGenerator.generate()),
                accountModel.getPayUserId(),
                creditLoanRechargeDto.getAmount());
        try {
            ProjectTransferNopwdResponseModel responseModel = paySyncClient.send(
                    CreditLoanRechargeNopwdMapper.class,
                    requestModel,
                    ProjectTransferNopwdResponseModel.class);
            payDataDto.setStatus(responseModel.isSuccess());
            payDataDto.setCode(responseModel.getRetCode());
            payDataDto.setMessage(responseModel.getRetMsg());
            creditLoanRechargeMapper.create(model);
            return baseDto;
        } catch (PayException e) {
            payDataDto.setStatus(false);
            payDataDto.setMessage(e.getLocalizedMessage());
            logger.error(e.getLocalizedMessage(), e);
        }
        return baseDto;
    }

    @Override
    @Transactional
    public BaseDto<PayFormDataDto> creditLoanRecharge(CreditLoanRechargeDto creditLoanRechargeDto) {
        BaseDto<PayFormDataDto> dto = new BaseDto<>();
        PayFormDataDto payFormDataDto = new PayFormDataDto();
        dto.setData(payFormDataDto);

        UserModel userModel = userMapper.findByMobile(creditLoanRechargeDto.getMobile());

        CreditLoanRechargeModel creditLoanRechargeModel = new CreditLoanRechargeModel(creditLoanRechargeDto, userModel.getLoginName());
        creditLoanRechargeModel.setId(IdGenerator.generate());

        AccountModel accountModel = accountMapper.findByLoginName(creditLoanRechargeModel.getLoginName());

        String remark = MessageFormat.format("{0} 从 {1} 账户为信用贷标的账户充值 {2} 元", creditLoanRechargeDto.getOperatorLoginName(),
                userModel.getMobile(), creditLoanRechargeDto.getAmount());
        creditLoanRechargeModel.setRemark(remark);

        ProjectTransferRequestModel requestModel = ProjectTransferRequestModel.newCreditLoanRequest(
                null,
                String.valueOf(IdGenerator.generate()),
                accountModel.getPayUserId(),
                String.valueOf(creditLoanRechargeDto.getAmount()));

        try {
            BaseDto<PayFormDataDto> baseDto = payAsyncClient.generateFormData(CreditLoanRechargePwdMapper.class, requestModel);
            creditLoanRechargeMapper.create(creditLoanRechargeModel);
            return baseDto;
        } catch (PayException e) {
            logger.error(MessageFormat.format("{0} purchase credit loan  is failed", creditLoanRechargeDto.getOperatorLoginName()), e);
        }
        return dto;
    }

    @Override
    public String creditLoanRechargeCallback(Map<String, String> paramsMap, String originalQueryString) {
        BaseCallbackRequestModel callbackRequest = this.payAsyncClient.parseCallbackRequest(paramsMap, originalQueryString, TransferNotifyMapper.class, TransferNotifyRequestModel.class);

        if (callbackRequest == null) {
            return null;
        }

        this.postCreditLoanRechargeCallback(callbackRequest);

        return callbackRequest.getResponseData();
    }

    @Transactional
    private void postCreditLoanRechargeCallback(BaseCallbackRequestModel callbackRequestModel) {
        try {
            long orderId = Long.parseLong(callbackRequestModel.getOrderId());
            CreditLoanRechargeModel creditLoanRechargeModel = creditLoanRechargeMapper.findById(orderId);
            if (creditLoanRechargeModel == null) {
                logger.error(MessageFormat.format("credit_loan_recharge callback order is not exist (orderId = {0})", callbackRequestModel.getOrderId()));
                return;
            }
            if (creditLoanRechargeModel.getStatus() != RechargeStatus.WAIT_PAY) {
                logger.error(MessageFormat.format("credit loan has dealt with the credit loan recharge (orderId = {0})", callbackRequestModel.getOrderId()));
                return;
            }
            String loginName = creditLoanRechargeModel.getLoginName();
            long amount = creditLoanRechargeModel.getAmount();
            if (callbackRequestModel.isSuccess()) {
                creditLoanRechargeModel.setSuccessTime(new Date());
                creditLoanRechargeModel.setStatus(RechargeStatus.SUCCESS);
                creditLoanRechargeMapper.updateCreditLoanRecharge(creditLoanRechargeModel);
                try {
                    amountTransfer.transferOutBalance(loginName, orderId, amount, UserBillBusinessType.CREDIT_LOAN_RECHARGE, null, null);
                    creditLoanBillService.transferIn(orderId, amount, CreditLoanBillBusinessType.CREDIT_LOAN_RECHARGE,
                            MessageFormat.format("{0}充值到信用贷标的账户{1}", loginName, amount), loginName);
                } catch (AmountTransferException e) {
                    logger.error(MessageFormat.format("credit loan recharge transfer out balance failed (orderId = {0})", String.valueOf(callbackRequestModel.getOrderId())));
                }

            } else {
                creditLoanRechargeModel.setStatus(RechargeStatus.FAIL);
                creditLoanRechargeMapper.updateCreditLoanRecharge(creditLoanRechargeModel);
            }
        } catch (NumberFormatException e) {
            logger.error(MessageFormat.format("credit loan Recharge callback order is not a number (orderId = {0})", callbackRequestModel.getOrderId()));
            logger.error(e.getLocalizedMessage(), e);
        }
    }
}
