package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.RetrievePasswordRequestDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.api.service.v1_0.MobileAppRetrievePasswordService;
import com.tuotiansudai.api.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(description = "找回密码")
public class MobileAppRetrievePasswordController extends MobileAppBaseController {

    @Autowired
    private MobileAppRetrievePasswordService retrievePasswordService;

    /**
     * @param retrievePasswordRequestDto
     * @return BaseResponseDto
     * @function 找回密码
     */
    @RequestMapping(value = "/retrievepassword", method = RequestMethod.POST)
    @ApiOperation("找回密码")
    public BaseResponseDto retrievePassword(@Valid @RequestBody RetrievePasswordRequestDto retrievePasswordRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorCode = bindingResult.getFieldError().getDefaultMessage();
            String errorMessage = ReturnMessage.getErrorMsgByCode(errorCode);
            return new BaseResponseDto(errorCode, errorMessage);
        } else {
            return retrievePasswordService.retrievePassword(retrievePasswordRequestDto);
        }
    }

}
