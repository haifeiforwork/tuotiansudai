package com.tuotiansudai.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.tuotiansudai.client.PayWrapperClient;
import com.tuotiansudai.coupon.repository.mapper.CouponMapper;
import com.tuotiansudai.coupon.repository.mapper.CouponRepayMapper;
import com.tuotiansudai.coupon.repository.mapper.UserCouponMapper;
import com.tuotiansudai.coupon.repository.model.CouponRepayModel;
import com.tuotiansudai.coupon.repository.model.UserCouponModel;
import com.tuotiansudai.dto.*;
import com.tuotiansudai.repository.mapper.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.service.RepayService;
import com.tuotiansudai.util.AmountConverter;
import com.tuotiansudai.util.AmountTransfer;
import com.tuotiansudai.util.InterestCalculator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RepayServiceImpl implements RepayService {

    private static Logger logger = Logger.getLogger(RepayServiceImpl.class);

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private PayWrapperClient payWrapperClient;

    @Autowired
    private LoanRepayMapper loanRepayMapper;

    @Autowired
    private InvestRepayMapper investRepayMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponRepayMapper couponRepayMapper;

    @Override
    public BaseDto<PayFormDataDto> repay(RepayDto repayDto) {
        return payWrapperClient.repay(repayDto);
    }

    @Override
    public BaseDto<LoanerLoanRepayDataDto> getLoanRepay(String loginName, long loanId) {
        BaseDto<LoanerLoanRepayDataDto> baseDto = new BaseDto<>();
        LoanerLoanRepayDataDto dataDto = new LoanerLoanRepayDataDto();
        baseDto.setData(dataDto);

        LoanModel loanModel = loanMapper.findById(loanId);
        List<LoanRepayModel> loanRepayModels = loanRepayMapper.findByAgentAndLoanId(loginName, loanId);
        if (loanRepayModels.isEmpty()) {
            logger.error(MessageFormat.format("login user({0}) is not agent({1}) of loan({2})",
                    loginName, loanModel.getAgentLoginName(), String.valueOf(loanId)));
            return baseDto;
        }

        final LoanRepayModel enabledLoanRepayModel = loanRepayMapper.findEnabledLoanRepayByLoanId(loanId);
        boolean isWaitPayLoanRepayExist = Iterators.any(loanRepayModels.iterator(), new Predicate<LoanRepayModel>() {
            @Override
            public boolean apply(LoanRepayModel input) {
                return input.getStatus() == RepayStatus.WAIT_PAY;
            }
        });

        boolean isRepayingLoanRepayExist = Iterators.any(loanRepayModels.iterator(), new Predicate<LoanRepayModel>() {
            @Override
            public boolean apply(LoanRepayModel input) {
                return input.getStatus() == RepayStatus.REPAYING;
            }
        });

        dataDto.setLoanId(loanId);
        dataDto.setLoanerBalance(AmountConverter.convertCentToString(accountMapper.findByLoginName(loginName).getBalance()));

        if (enabledLoanRepayModel != null && !isWaitPayLoanRepayExist) {
            dataDto.setNormalRepayEnabled(true);
            long defaultInterest = 0;
            for (LoanRepayModel loanRepayModel : loanRepayModels) {
                defaultInterest += loanRepayModel.getDefaultInterest();
            }
            dataDto.setNormalRepayAmount(AmountConverter.convertCentToString(enabledLoanRepayModel.getCorpus() + enabledLoanRepayModel.getExpectedInterest() + defaultInterest));
        }

        if (loanModel.getStatus() != LoanStatus.OVERDUE && isRepayingLoanRepayExist && !isWaitPayLoanRepayExist) {
            dataDto.setAdvanceRepayEnabled(true);
            DateTime now = new DateTime();
            DateTime lastSuccessRepayDate = InterestCalculator.getLastSuccessRepayDate(loanModel, loanRepayModels);
            List<InvestModel> successInvests = investMapper.findSuccessInvestsByLoanId(loanId);
            long advanceRepayInterest = InterestCalculator.calculateLoanRepayInterest(loanModel, successInvests, lastSuccessRepayDate, now);
            long corpus = loanRepayMapper.findLastLoanRepay(loanId).getCorpus();
            dataDto.setAdvanceRepayAmount(AmountConverter.convertCentToString(corpus + advanceRepayInterest));
        }

        List<LoanerLoanRepayDataItemDto> records = Lists.transform(loanRepayModels, new Function<LoanRepayModel, LoanerLoanRepayDataItemDto>() {
            @Override
            public LoanerLoanRepayDataItemDto apply(LoanRepayModel loanRepayModel) {
                boolean isEnabledLoanRepay = enabledLoanRepayModel != null && loanRepayModel.getId() == enabledLoanRepayModel.getId();
                return new LoanerLoanRepayDataItemDto(loanRepayModel, isEnabledLoanRepay);
            }
        });

        dataDto.setStatus(true);
        dataDto.setRecords(records);
        return baseDto;
    }

    @Override
    @Transactional
    public void resetPayExpiredLoanRepay(long loanId) {
        LoanRepayModel enabledLoanRepay = loanRepayMapper.findEnabledLoanRepayByLoanId(loanId);
        if (enabledLoanRepay != null && enabledLoanRepay.getStatus() == RepayStatus.WAIT_PAY &&
                new DateTime(enabledLoanRepay.getActualRepayDate()).plusMinutes(30).isBefore(new DateTime())) {
            enabledLoanRepay.setActualInterest(0);
            enabledLoanRepay.setRepayAmount(0);
            enabledLoanRepay.setStatus(enabledLoanRepay.getActualRepayDate().before(enabledLoanRepay.getRepayDate()) ? RepayStatus.REPAYING : RepayStatus.OVERDUE);
            enabledLoanRepay.setActualRepayDate(null);
            loanRepayMapper.update(enabledLoanRepay);
        }
    }

    @Override
    public BaseDto<InvestRepayDataDto> findInvestorInvestRepay(String loginName, long investId) {
        BaseDto<InvestRepayDataDto> baseDto = new BaseDto<>();
        InvestRepayDataDto dataDto = new InvestRepayDataDto();
        dataDto.setStatus(true);
        dataDto.setRecords(Lists.<InvestRepayDataItemDto>newArrayList());
        baseDto.setData(dataDto);
        List<UserCouponModel> userCouponModels = userCouponMapper.findByInvestId(investId);
        List<InvestRepayModel> investRepayModels = investRepayMapper.findByLoginNameAndInvestId(loginName, investId);
        if (CollectionUtils.isNotEmpty(investRepayModels)) {
            List<InvestRepayDataItemDto> records = Lists.transform(investRepayModels, new Function<InvestRepayModel, InvestRepayDataItemDto>() {
                @Override
                public InvestRepayDataItemDto apply(InvestRepayModel investRepayModel) {
                    return new InvestRepayDataItemDto(investRepayModel);
                }
            });

            List<CouponRepayModel> userCouponByInvestId = couponRepayMapper.findByUserCouponByInvestId(investId);
            if(CollectionUtils.isNotEmpty(userCouponByInvestId)){
                for(InvestRepayDataItemDto investRepayDataItemDto : records){
                    final Date repayDate = investRepayDataItemDto.getRepayDate();
                    Optional<CouponRepayModel> couponRepay =  Iterators.tryFind(userCouponByInvestId.iterator(), new Predicate<CouponRepayModel>() {
                        @Override
                        public boolean apply(CouponRepayModel input) {
                            return input.getRepayDate() == repayDate;
                        }
                    });

                    if(couponRepay.isPresent()){
                        investRepayDataItemDto.setCouponExpectedInterest(AmountConverter.convertCentToString(couponRepay.get().getExpectedInterest()));
                        if(RepayStatus.COMPLETE.name() == investRepayDataItemDto.getStatus()){
                            investRepayDataItemDto.setActualFee(AmountConverter.convertCentToString(AmountConverter.convertStringToCent(investRepayDataItemDto.getActualFee()) + couponRepay.get().getActualFee()));
                            investRepayDataItemDto.setActualInterest(AmountConverter.convertCentToString(AmountConverter.convertStringToCent(investRepayDataItemDto.getActualInterest()) + couponRepay.get().getActualInterest()));
                        }
                    }
                }
            }
            dataDto.setRecords(records);
        }
        return baseDto;
    }
}
