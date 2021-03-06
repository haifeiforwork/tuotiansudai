package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.InvestNoPassResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.InvestRequestDto;
import com.tuotiansudai.api.dto.v1_0.InvestResponseDataDto;
import com.tuotiansudai.api.service.v1_0.MobileAppInvestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(description = "出借")
public class MobileAppInvestController extends MobileAppBaseController {
    @Autowired
    private MobileAppInvestService mobileAppInvestService;

    @RequestMapping(value = "/create/invest", method = RequestMethod.POST)
    @ApiOperation("验密直投")
    public BaseResponseDto<InvestResponseDataDto> invest(@RequestBody InvestRequestDto investRequestDto) {
        investRequestDto.setUserId(getLoginName());
        investRequestDto.getBaseParam().setUserId(getLoginName());
        return mobileAppInvestService.invest(investRequestDto);
    }

    @RequestMapping(value = "/no-password-invest", method = RequestMethod.POST)
    @ApiOperation("免密出借")
    public BaseResponseDto<InvestNoPassResponseDataDto> noPasswordInvest(@RequestBody InvestRequestDto investRequestDto) {
        investRequestDto.setUserId(getLoginName());
        investRequestDto.getBaseParam().setUserId(getLoginName());
        return mobileAppInvestService.noPasswordInvest(investRequestDto);
    }

}
