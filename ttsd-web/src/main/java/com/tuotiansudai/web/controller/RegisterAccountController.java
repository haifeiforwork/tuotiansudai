package com.tuotiansudai.web.controller;

import com.tuotiansudai.dto.BaseDataDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.RegisterAccountDto;
import com.tuotiansudai.service.AccountService;
import com.tuotiansudai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/register/account")
public class RegisterAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registerAccount() {
        return new ModelAndView("/register-account");
    }

    @RequestMapping(value = "/identity-number/{identityNumber:^[1-9]\\d{13,16}[a-zA-Z0-9]{1}$}/is-exist", method = RequestMethod.GET)
    @ResponseBody
    public BaseDto<BaseDataDto> isIdentityNumberExist(@PathVariable String identityNumber) {
        boolean isExist = accountService.isIdentityNumberExist(identityNumber);
        BaseDto<BaseDataDto> baseDto = new BaseDto<>();
        BaseDataDto dataDto = new BaseDataDto();
        baseDto.setData(dataDto);
        dataDto.setStatus(isExist);

        return baseDto;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registerAccount(@Valid @ModelAttribute RegisterAccountDto registerAccountDto, RedirectAttributes redirectAttributes) {
        BaseDto<PayDataDto> dto = this.userService.registerAccount(registerAccountDto);
        boolean isRegisterSuccess = dto.getData().getStatus();
        if (!isRegisterSuccess) {
            redirectAttributes.addFlashAttribute("originalFormData", registerAccountDto);
            redirectAttributes.addFlashAttribute("success", isRegisterSuccess);
        }

        return new ModelAndView(isRegisterSuccess ? "redirect:/" : "redirect:/register/account");
    }
}
