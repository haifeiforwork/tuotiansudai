package com.tuotiansudai.paywrapper.controller;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.dto.RepayDto;
import com.tuotiansudai.paywrapper.service.AdvanceRepayService;
import com.tuotiansudai.paywrapper.service.NormalRepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RepayController {

    @Autowired
    private NormalRepayService normalRepayService;

    @Autowired
    private AdvanceRepayService advanceRepayService;

    @RequestMapping(value = "/repay", method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<PayFormDataDto> repay(@Valid @RequestBody RepayDto dto) {
        return dto.isAdvanced() ? advanceRepayService.repay(dto.getLoanId()) : normalRepayService.repay(dto.getLoanId());
    }
}
