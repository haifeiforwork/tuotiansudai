package com.tuotiansudai.web.controller;

import com.tuotiansudai.dto.HomeLoanDto;
import com.tuotiansudai.service.HomeService;
import com.tuotiansudai.service.UserService;
import com.tuotiansudai.web.freemarker.directive.AmountDirective;
import com.tuotiansudai.web.freemarker.directive.PercentFractionDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/index");
        List<HomeLoanDto> loans = homeService.getLoans();
        int userCount = userService.findUserCount();
        modelAndView.addObject("loans", loans);
        modelAndView.addObject("percentFraction",new PercentFractionDirective());
        modelAndView.addObject("amount",new AmountDirective());
        modelAndView.addObject("userCount",userCount);
        return modelAndView;
    }

}
