package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.InvestRequestDto;
import com.tuotiansudai.api.dto.v1_0.UserInvestRepayRequestDto;
import com.tuotiansudai.api.service.v1_0.MobileAppInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MobileAppUserInvestRepayController extends MobileAppBaseController {
    @Autowired
    private MobileAppInvestService mobileAppInvestService;

    @RequestMapping(value = "/get/user-invest-repay", method = RequestMethod.POST)
    public BaseResponseDto invest(@RequestBody UserInvestRepayRequestDto userInvestRepayRequestDto) {

        return mobileAppInvestService.invest(investRequestDto);
    }
}
