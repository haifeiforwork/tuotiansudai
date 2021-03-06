package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ExchangeRequestDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.api.dto.v1_0.UserCouponListResponseDataDto;
import com.tuotiansudai.api.service.v1_0.MobileAppExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(description = "兑换优惠券")
public class MobileAppExchangeController extends MobileAppBaseController{

    @Autowired
    private MobileAppExchangeService mobileAppExchangeService;

    @RequestMapping(value = "/get/code-exchange", method = RequestMethod.POST)
    @ApiOperation("兑换优惠券")
    public BaseResponseDto<UserCouponListResponseDataDto> exchange(@Valid @RequestBody ExchangeRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorCode = fieldError.getDefaultMessage();
            String errorMessage = ReturnMessage.getErrorMsgByCode(errorCode);
            return new BaseResponseDto(errorCode, errorMessage);
        }
        requestDto.getBaseParam().setUserId(getLoginName());
        return mobileAppExchangeService.exchange(requestDto);
    }

}
