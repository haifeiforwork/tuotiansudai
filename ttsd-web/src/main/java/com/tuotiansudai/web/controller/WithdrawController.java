package com.tuotiansudai.web.controller;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.dto.WithdrawDto;
import com.tuotiansudai.repository.model.BankCardModel;
import com.tuotiansudai.service.AccountService;
import com.tuotiansudai.service.BindBankCardService;
import com.tuotiansudai.service.BlacklistService;
import com.tuotiansudai.service.WithdrawService;
import com.tuotiansudai.util.AmountConverter;
import com.tuotiansudai.spring.LoginUserInfo;
import com.tuotiansudai.util.BankCardUtil;
import com.tuotiansudai.web.config.interceptors.MobileAccessDecision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BindBankCardService bindBankCardService;

    @Autowired
    private BlacklistService blacklistService;

    @Value("${pay.withdraw.fee}")
    private long withdrawFee;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView withdraw() {
        BankCardModel bankCard = bindBankCardService.getPassedBankCard(LoginUserInfo.getLoginName());
        if (bankCard == null) {
            if(MobileAccessDecision.isMobileAccess()){
                return new ModelAndView("redirect:/m/bind-card");
            }
            return new ModelAndView("redirect:/bind-card");
        }
        long balance = accountService.getBalance(LoginUserInfo.getLoginName());
        boolean hasAccess = !blacklistService.userIsInBlacklist(LoginUserInfo.getLoginName());
        ModelAndView modelAndView = new ModelAndView("/withdraw");
        modelAndView.addObject("balance", AmountConverter.convertCentToString(balance));
        modelAndView.addObject("withdrawFee", AmountConverter.convertCentToString(withdrawFee));
        modelAndView.addObject("hasAccess", String.valueOf(hasAccess));
        modelAndView.addObject("bankCard", bankCard);
        modelAndView.addObject("bankName", BankCardUtil.getBankName(bankCard.getBankCode()));
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView withdraw(@Valid @ModelAttribute WithdrawDto withdrawDto) {
        String loginName = LoginUserInfo.getLoginName();
        if (blacklistService.userIsInBlacklist(loginName)) {
            return new ModelAndView("/");
        }
        withdrawDto.setLoginName(loginName);
        BaseDto<PayFormDataDto> baseDto = withdrawService.withdraw(withdrawDto);
        return new ModelAndView("/pay", "pay", baseDto);
    }
}
