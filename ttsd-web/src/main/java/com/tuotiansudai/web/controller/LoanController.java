package com.tuotiansudai.web.controller;


import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @RequestMapping(value = "/{loanId}", method = RequestMethod.GET)
    public ModelAndView getLoanDetail(@PathVariable long loanId) {
        BaseDto dto = loanService.getLoanDetail(loanId);
        return new ModelAndView("/loan", "baseDto", dto);
    }

    @RequestMapping(value = "/{loanId}/index/{index}/pagesize/{pagesize}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getInvestList(@PathVariable long loanId, @PathVariable int index, @PathVariable int pagesize) {
        BaseDto<BasePaginationDataDto> dto = loanService.getInvests(loanId, index, pagesize);
        return new ModelAndView("/loan", "baseDto", dto);
    }


}
