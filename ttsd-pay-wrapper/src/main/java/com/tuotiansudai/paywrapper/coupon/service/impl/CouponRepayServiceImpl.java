package com.tuotiansudai.paywrapper.coupon.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tuotiansudai.coupon.repository.mapper.CouponMapper;
import com.tuotiansudai.coupon.repository.mapper.UserCouponMapper;
import com.tuotiansudai.coupon.repository.model.CouponModel;
import com.tuotiansudai.coupon.repository.model.UserCouponModel;
import com.tuotiansudai.paywrapper.client.PaySyncClient;
import com.tuotiansudai.paywrapper.coupon.service.CouponRepayService;
import com.tuotiansudai.paywrapper.exception.PayException;
import com.tuotiansudai.paywrapper.repository.mapper.TransferMapper;
import com.tuotiansudai.paywrapper.repository.model.sync.request.TransferRequestModel;
import com.tuotiansudai.paywrapper.repository.model.sync.response.TransferResponseModel;
import com.tuotiansudai.paywrapper.service.SystemBillService;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.LoanMapper;
import com.tuotiansudai.repository.mapper.LoanRepayMapper;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.transfer.repository.mapper.TransferApplicationMapper;
import com.tuotiansudai.transfer.repository.model.TransferApplicationModel;
import com.tuotiansudai.util.AmountTransfer;
import com.tuotiansudai.util.InterestCalculator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class CouponRepayServiceImpl implements CouponRepayService {

    static Logger logger = Logger.getLogger(CouponRepayServiceImpl.class);

    public static final List<CouponType> COUPON_TYPE_LIST = Lists.newArrayList(CouponType.NEWBIE_COUPON,
            CouponType.INVEST_COUPON,
            CouponType.INTEREST_COUPON,
            CouponType.BIRTHDAY_COUPON);

    private static final String COUPON_ORDER_ID_TEMPLATE = "{0}X{1}";

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private LoanRepayMapper loanRepayMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private PaySyncClient paySyncClient;

    @Autowired
    private AmountTransfer amountTransfer;

    @Autowired
    private SystemBillService systemBillService;

    @Autowired
    private TransferApplicationMapper transferApplicationMapper;

    @Override
    public void repay(long loanRepayId) {
        logger.info(MessageFormat.format("[Coupon Repay {0}] coupon repay is starting...", String.valueOf(loanRepayId)));
        LoanRepayModel currentLoanRepayModel = this.loanRepayMapper.findById(loanRepayId);
        LoanModel loanModel = loanMapper.findById(currentLoanRepayModel.getLoanId());
        List<LoanRepayModel> loanRepayModels = this.loanRepayMapper.findByLoanIdOrderByPeriodAsc(currentLoanRepayModel.getLoanId());
        List<UserCouponModel> userCouponModels = userCouponMapper.findByLoanId(loanModel.getId(), COUPON_TYPE_LIST);

        LoanRepayModel lastLoanRepayModel = this.getLastLoanRepayModel(currentLoanRepayModel, loanRepayModels);

        for (UserCouponModel userCouponModel : userCouponModels) {
            List<TransferApplicationModel> transferApplicationModels = transferApplicationMapper.findByTransferInvestId(userCouponModel.getInvestId(),Lists.newArrayList(TransferStatus.SUCCESS));
            if(CollectionUtils.isNotEmpty(transferApplicationModels)){
                logger.info(MessageFormat.format("ID:{0},LOAN_ID:{1},LOAN_REPAY_ID:{2},INVEST_ID:{3} has transferred",
                        String.valueOf(userCouponModel.getId()),
                        userCouponModel.getLoanId() == null?"":userCouponModel.getLoanId(),
                        String.valueOf(loanRepayId),
                        userCouponModel.getInvestId() == null?"":userCouponModel.getInvestId()));
               continue;
            }
            CouponModel couponModel = this.couponMapper.findById(userCouponModel.getCouponId());
            long investAmount = investMapper.findById(userCouponModel.getInvestId()).getAmount();
            long actualInterest = InterestCalculator.calculateCouponActualInterest(investAmount, couponModel, userCouponModel, loanModel, currentLoanRepayModel, loanRepayModels);
            if (actualInterest < 0) {
                continue;
            }
            long actualFee = (long) (actualInterest * loanModel.getInvestFeeRate());
            long transferAmount = actualInterest - actualFee;
            logger.info(MessageFormat.format("[Coupon Repay {0}] user coupon({1}) is {2}, repay amount is  {3}({4} - {5})",
                    String.valueOf(currentLoanRepayModel.getId()),
                    String.valueOf(userCouponModel.getId()),
                    couponModel.getCouponType().name(),
                    String.valueOf(transferAmount),
                    String.valueOf(actualInterest),
                    String.valueOf(actualFee)));

            boolean isTransferSuccess = transferAmount == 0;
            if (transferAmount > 0) {
                try {
                    TransferRequestModel requestModel = TransferRequestModel.newRequest(MessageFormat.format(COUPON_ORDER_ID_TEMPLATE, String.valueOf(userCouponModel.getId()), String.valueOf(new Date().getTime())),
                            accountMapper.findByLoginName(userCouponModel.getLoginName()).getPayUserId(),
                            String.valueOf(transferAmount));
                    TransferResponseModel responseModel = paySyncClient.send(TransferMapper.class, requestModel, TransferResponseModel.class);
                    isTransferSuccess = responseModel.isSuccess();
                    logger.info(MessageFormat.format("[Coupon Repay {0}] user coupon({1}) transfer status is {2}",
                            String.valueOf(currentLoanRepayModel.getId()),
                            String.valueOf(userCouponModel.getId()),
                            String.valueOf(isTransferSuccess)));
                } catch (PayException e) {
                    logger.error(MessageFormat.format("[Coupon Repay {0}] user coupon({1}) transfer is failed",
                            String.valueOf(currentLoanRepayModel.getId()),
                            String.valueOf(userCouponModel.getId())), e);
                }
            }

            if (isTransferSuccess) {
                try {
                    userCouponModel.setActualInterest(userCouponModel.getActualInterest() + actualInterest);
                    userCouponModel.setActualFee(userCouponModel.getActualFee() + actualFee);
                    userCouponMapper.update(userCouponModel);

                    amountTransfer.transferInBalance(userCouponModel.getLoginName(),
                            userCouponModel.getId(),
                            actualInterest,
                            couponModel.getCouponType().getUserBillBusinessType(), null, null);

                    amountTransfer.transferOutBalance(userCouponModel.getLoginName(),
                            userCouponModel.getId(),
                            actualFee,
                            UserBillBusinessType.INVEST_FEE, null, null);

                    String detail = MessageFormat.format(SystemBillDetailTemplate.COUPON_INTEREST_DETAIL_TEMPLATE.getTemplate(),
                            couponModel.getCouponType().getName(),
                            String.valueOf(userCouponModel.getId()),
                            String.valueOf(currentLoanRepayModel.getId()),
                            String.valueOf(transferAmount));

                    systemBillService.transferOut(userCouponModel.getId(), transferAmount, SystemBillBusinessType.COUPON, detail);

                    logger.info(MessageFormat.format("[Coupon Repay {0}] user coupon({1}) update user bill and system bill is success",
                            String.valueOf(currentLoanRepayModel.getId()),
                            String.valueOf(userCouponModel.getId())));
                } catch (Exception e) {
                    logger.error(MessageFormat.format("[Coupon Repay {0}] user coupon({1}) update user bill is failed",
                            String.valueOf(currentLoanRepayModel.getId()),
                            String.valueOf(userCouponModel.getId())), e);
                }
            }
        }

        logger.info(MessageFormat.format("[Coupon Repay {0}] coupon repay is done", String.valueOf(loanRepayId)));
    }

    private LoanRepayModel getLastLoanRepayModel(LoanRepayModel currentLoanRepayModel, List<LoanRepayModel> loanRepayModels) {
        LoanRepayModel lastLoanRepayModel = null;
        for (LoanRepayModel loanRepayModel : loanRepayModels) {
            if (loanRepayModel.getPeriod() < currentLoanRepayModel.getPeriod() && loanRepayModel.getStatus() == RepayStatus.COMPLETE && loanRepayModel.getActualRepayDate().before(currentLoanRepayModel.getActualRepayDate())) {
                lastLoanRepayModel = loanRepayModel;
            }
        }
        return lastLoanRepayModel;
    }
}