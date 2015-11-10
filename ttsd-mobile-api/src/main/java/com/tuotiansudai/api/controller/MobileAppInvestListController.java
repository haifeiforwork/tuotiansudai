package com.tuotiansudai.api.controller;

import com.tuotiansudai.api.dto.BaseResponseDto;
import com.tuotiansudai.api.dto.InvestListRequestDto;
import com.tuotiansudai.api.dto.UserInvestListRequestDto;
import com.tuotiansudai.api.service.MobileAppInvestListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileAppInvestListController extends MobileAppBaseController {
    @Autowired
    private MobileAppInvestListService mobileAppInvestListService;

    @RequestMapping(value = "/get/invests", method = RequestMethod.POST)
    public BaseResponseDto queryInvestList(@RequestBody InvestListRequestDto investListRequestDto) {
        return mobileAppInvestListService.generateInvestList(investListRequestDto);
    }

    @RequestMapping(value = "/get/userinvests", method = RequestMethod.POST)
    public BaseResponseDto queryUserInvestList(@RequestBody UserInvestListRequestDto requestDto) {
        return mobileAppInvestListService.generateUserInvestList(requestDto);
    }
}
