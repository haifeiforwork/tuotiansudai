package com.tuotiansudai.security;

import com.tuotiansudai.exception.CaptchaNotMatchException;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.util.CaptchaHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CaptchaHelper captchaHelper;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);

        UserModel userModel = userMapper.findByLoginNameOrMobile(httpServletRequest.getParameter("username"));
        boolean enabled = userModel.isActive();
        if (!enabled) {
            String errorMessage = MessageFormat.format("Login Error: {0} is locked!", httpServletRequest.getParameter("username"));
            throw new DisabledException(errorMessage);
        }

        String captcha = httpServletRequest.getParameter("captcha");
        boolean result = this.captchaHelper.captchaVerify(CaptchaHelper.LOGIN_CAPTCHA, captcha);

        if (!result) {
            logger.debug("Authentication failed: captcha does not match actual value");
            throw new CaptchaNotMatchException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.captchaNotMatch", "Captcha Not Match"));
        }
    }

    public void setCaptchaHelper(CaptchaHelper captchaHelper) {
        this.captchaHelper = captchaHelper;
    }

}
