package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.LoanListRequestDto;
import com.tuotiansudai.api.dto.v1_0.LoanListResponseDataDto;
import com.tuotiansudai.api.service.v1_0.MobileAppLoanListService;
import com.tuotiansudai.spring.LoginUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@Api(description = "标的列表")
public class MobileAppLoanListController extends MobileAppBaseController {
    static Logger logger = Logger.getLogger(MobileAppLoanListController.class);
    @Autowired
    private MobileAppLoanListService mobileAppLoanListService;

    @RequestMapping(value = "/get/loans", method = RequestMethod.POST)
    @ApiOperation("标的列表")
    public BaseResponseDto<LoanListResponseDataDto> queryLoanList(@RequestBody LoanListRequestDto loanListRequestDto) {
        logger.info(MessageFormat.format("queryLoanList loginName:{0}", LoginUserInfo.getLoginName()));
        logger.info(MessageFormat.format("queryLoanList mobile:{0}", LoginUserInfo.getMobile()));
        logger.info(MessageFormat.format("queryLoanList token:{0}", LoginUserInfo.getToken()));
        loanListRequestDto.getBaseParam().setUserId(getLoginName());
        return mobileAppLoanListService.generateLoanList(loanListRequestDto);
    }
}
