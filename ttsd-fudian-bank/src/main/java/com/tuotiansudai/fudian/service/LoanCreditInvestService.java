package com.tuotiansudai.fudian.service;

import com.google.common.base.Strings;
import com.tuotiansudai.fudian.config.ApiType;
import com.tuotiansudai.fudian.dto.request.LoanCreditInvestRequestDto;
import com.tuotiansudai.fudian.dto.response.ResponseDto;
import com.tuotiansudai.fudian.mapper.InsertMapper;
import com.tuotiansudai.fudian.mapper.UpdateMapper;
import com.tuotiansudai.fudian.sign.SignatureHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanCreditInvestService implements AsyncCallbackInterface {

    private static Logger logger = LoggerFactory.getLogger(LoanCreditInvestService.class);

    private final SignatureHelper signatureHelper;

    private final InsertMapper insertMapper;

    private final UpdateMapper updateMapper;

    @Autowired
    public LoanCreditInvestService(SignatureHelper signatureHelper, InsertMapper insertMapper, UpdateMapper updateMapper) {
        this.signatureHelper = signatureHelper;
        this.insertMapper = insertMapper;
        this.updateMapper = updateMapper;
    }

    public LoanCreditInvestRequestDto invest(String userName, String accountNo, String loanTxNo, String investOrderNo, String investOrderDate, String creditNo, String creditAmount, String amount, String creditFee) {
        LoanCreditInvestRequestDto dto = new LoanCreditInvestRequestDto(userName, accountNo, loanTxNo, investOrderNo, investOrderDate, creditNo, creditAmount, amount, creditFee);
        signatureHelper.sign(dto);

        if (Strings.isNullOrEmpty(dto.getRequestData())) {
            logger.error("[loan credit invest] sign error, userName: {}, accountNo: {}, loanTxNo: {}, investOrderNo: {}, investOrderDate: {}, creditNo: {}, creditAmount: {}, amount: {}, creditFee: {}",
                    userName, accountNo, loanTxNo, investOrderNo, investOrderDate, creditNo, creditAmount, amount, creditFee);
            return null;
        }

        insertMapper.insertLoanCreditInvest(dto);
        return dto;
    }

    @Override
    public ResponseDto callback(String responseData) {
        logger.info("[loan credit invest] data is {}", responseData);
        ResponseDto responseDto = ApiType.LOAN_CREDIT_INVEST.getParser().parse(responseData);

        if (responseDto == null) {
            logger.error("[loan credit invest] parse callback data error, data is {}", responseData);
            return null;
        }
        responseDto.setReqData(responseData);
        updateMapper.updateLoanCreditInvest(responseDto);
        return responseDto;
    }
}
