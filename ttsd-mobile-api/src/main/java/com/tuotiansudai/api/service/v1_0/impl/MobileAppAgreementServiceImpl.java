package com.tuotiansudai.api.service.v1_0.impl;


import com.tuotiansudai.api.dto.v1_0.AgreementOperateRequestDto;
import com.tuotiansudai.api.dto.v1_0.AgreementOperateResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.api.service.v1_0.MobileAppAgreementService;
import com.tuotiansudai.api.util.CommonUtils;
import com.tuotiansudai.client.MQWrapperClient;
import com.tuotiansudai.client.PayWrapperClient;
import com.tuotiansudai.dto.AgreementDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.repository.mapper.BankAccountMapper;
import com.tuotiansudai.repository.model.BankAccountModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MobileAppAgreementServiceImpl implements MobileAppAgreementService{

    static Logger logger = Logger.getLogger(MobileAppAgreementServiceImpl.class);

    @Autowired
    private PayWrapperClient payWrapperClient;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Autowired
    private MQWrapperClient mqWrapperClient;

    @Override
    public BaseResponseDto<AgreementOperateResponseDataDto> generateAgreementRequest(AgreementOperateRequestDto requestDto) {
        AgreementOperateResponseDataDto responseDataDto = new AgreementOperateResponseDataDto();
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        AgreementDto agreementDto = requestDto.convertToAgreementDto();
        BankAccountModel bankAccountModel = bankAccountMapper.findByLoginName(agreementDto.getLoginName());

        if (bankAccountModel != null && bankAccountModel.isAuthorization()) {
            baseResponseDto.setCode(ReturnMessage.AUTO_INVEST.getCode());
            baseResponseDto.setMessage(ReturnMessage.AUTO_INVEST.getMsg());
            baseResponseDto.setData(responseDataDto);
            return baseResponseDto;
        }

        BaseDto<PayFormDataDto> formDto = payWrapperClient.agreement(agreementDto);
        try {
            if (formDto.isSuccess()) {
                responseDataDto.setUrl(formDto.getData().getUrl());
                responseDataDto.setRequestData(CommonUtils.mapToFormData(formDto.getData().getFields()));
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getLocalizedMessage(), e);
            return new BaseResponseDto(ReturnMessage.UMPAY_INVEST_MESSAGE_INVALID.getCode(), ReturnMessage.UMPAY_INVEST_MESSAGE_INVALID.getMsg());
        }
        baseResponseDto.setCode(ReturnMessage.SUCCESS.getCode());
        baseResponseDto.setMessage(ReturnMessage.SUCCESS.getMsg());
        baseResponseDto.setData(responseDataDto);
        return baseResponseDto;
    }

}
