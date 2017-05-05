package com.tuotiansudai.api.service.v1_0;


import com.tuotiansudai.api.dto.v1_0.*;

public interface MobileAppTransferService {

    BaseResponseDto<TransferTransfereeResponseDataDto> getTransferee(TransferTransfereeRequestDto transferTransfereeRequestDto);

    BaseResponseDto<InvestResponseDataDto> transferPurchase(TransferPurchaseRequestDto transferPurchaseRequestDto);

    BaseResponseDto<InvestNoPassResponseDataDto> transferNoPasswordPurchase(TransferPurchaseRequestDto transferPurchaseRequestDto);

}
