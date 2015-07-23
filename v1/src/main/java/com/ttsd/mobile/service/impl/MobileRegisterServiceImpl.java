package com.ttsd.mobile.service.impl;

import com.esoft.archer.common.CommonConstants;
import com.esoft.archer.common.exception.AuthInfoAlreadyActivedException;
import com.esoft.archer.common.exception.AuthInfoOutOfDateException;
import com.esoft.archer.common.exception.NoMatchingObjectsException;
import com.esoft.archer.common.service.AuthService;
import com.esoft.archer.user.UserConstants;
import com.esoft.archer.user.model.Role;
import com.esoft.archer.user.model.User;
import com.esoft.archer.user.service.UserService;
import com.esoft.archer.user.service.impl.UserBO;
import com.esoft.core.annotations.Logger;
import com.esoft.core.util.DateStyle;
import com.esoft.core.util.DateUtil;
import com.esoft.core.util.HashCrypt;
import com.esoft.jdp2p.message.MessageConstants;
import com.esoft.jdp2p.message.model.UserMessageTemplate;
import com.esoft.jdp2p.message.service.impl.MessageBO;
import com.ttsd.aliyun.PropertiesUtils;
import com.ttsd.mobile.dao.IMobileRegisterDao;
import com.ttsd.mobile.service.IMobileRegisterService;
import com.ttsd.util.CommonUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tuotian on 15/7/9.
 */
@Service(value = "mobileRegisterServiceImpl")
public class MobileRegisterServiceImpl implements IMobileRegisterService {
    @Logger
    private Log log;

    @Resource(name = "mobileRegisterDaoImpl")
    private IMobileRegisterDao mobileRegisterDao;

    @Resource
    private AuthService authService;

    @Resource(name = "messageBO")
    private MessageBO messageBO;

    @Resource(name = "userBO")
    private UserBO userBO;

    @Resource(name = "userService")
    private UserService userService;

    /**
     * @function 手机端用户注册
     * @param userName 用户名
     * @param password 密码
     * @param phoneNum 手机号
     * @param vCode 验证码
     * @param referrer 推荐人
     * @return boolean 注册成功，返回true，否则返回false
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean mobileRegister(String userName, String password, String phoneNum, String vCode, String referrer) {
        //校验密码
        if (password == null || password.equals("")){
            log.info("提交的密码为空！");
            return false;
        }else if(password.length()<6){
            log.info("提交的密码长度是："+password.length()+" 位，少于6个字符");
            return false;
        }

        //校验手机号
        if (!regValidatePhoneNum(phoneNum)){
            return false;
        }

        int codeCount = 0;
        try {
            codeCount = mobileRegisterDao.getAuthInfo(phoneNum,vCode,CommonConstants.AuthInfoStatus.INACTIVE);
        }catch (Exception e){
            log.error("获取用户名为："+userName+",手机号为："+phoneNum+"的用户授权码信息失败！");
            log.error(e.getLocalizedMessage(),e);
            return false;
        }
        if(codeCount > 0) {
            User user = new User();
            user.setUsername(userName);
            user.setMobileNumber(phoneNum);
            user.setPassword(password);
            user.setReferrer(referrer);
            try {
                userService.registerByMobileNumber(user, vCode, referrer);
                log.info("用户名为："+userName+",手机号为："+phoneNum+"的用户信息持久化成功！");
            }catch (Exception e1){
                log.error("用户名为："+userName+",手机号为："+phoneNum+"的用户信息持久化失败！");
                log.error(e1.getLocalizedMessage(),e1);
                return false;
            }
            // 添加普通用户权限
            Role role = new Role("MEMBER");
            try {
                userBO.addRole(user, role);
                log.info("给用户名为：" + userName + ",手机号为：" + phoneNum + "的用户添加普通用户权限成功！");
                try {
                    //用户注册成功后，将发给该用户的注册码状态更新成已激活状态
                    mobileRegisterDao.updateUserAuthInfo(CommonConstants.AuthInfoStatus.ACTIVATED, phoneNum,CommonConstants.AuthInfoType.REGISTER_BY_MOBILE_NUMBER);
                }catch (Exception e){
                    log.error("更新用户名为："+userName+",手机号为："+phoneNum+"的授权码状态失败！");
                    log.error(e.getLocalizedMessage(), e);
                    return false;
                }
            }catch (Exception e){
                log.error("给用户名为："+userName+",手机号为："+phoneNum+"的用户添加普通用户权限失败！");
                log.error(e.getLocalizedMessage(),e);
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * @function 获取生成的授权码
     * @param phoneNumber 手机号
     * @return boolean 授权码发送成功，返回true，否则返回false
     */
    public boolean getCreatedValidateCode(String phoneNumber){
        if (regValidatePhoneNum(phoneNumber)) {
            try {
                boolean validateFlag = userService.sendRegisterByMobileNumberSMS(phoneNumber);
                if (!validateFlag){
                    log.info("手机号为："+phoneNumber+"的用户试图在一分钟内连续多次获取授权码！");
                    return false;
                }
                log.info("已为手机号为："+phoneNumber+"的用户成功创建授权码！");
            } catch (Exception e){
                log.error("生成授权码异常！");
                log.error(e.getLocalizedMessage(),e);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @function 校验用户注册的用户名是否已存在
     * @param userName 用户名
     * @return boolean 通过校验，返回ture,否则返回false
     */
    @Override
    public boolean validateUserName(String userName) {
        if (userName == null || userName.equals("")){
            log.info("提交的用户名为空！");
            return false;
        }else if(userName.length()<5){
            log.info("提交的用户名的长度为："+userName.length()+" ,少于五个字符！");
            return false;
        }else if(userName.length()>25){
            log.info("提交的用户名的长度为："+userName.length()+" ,多于二十五个字符！");
            return false;
        }else{
            int count = mobileRegisterDao.getUserCountByUserName(userName);
            log.info("用户名为" + userName + "的用户有" + count + "个！");
            if(count > 0){
                log.info("用户名为"+userName+"的用户已经存在！");
                return false;
            }
            return true;
        }
    }

    /**
     * @function 校验用注册的手机号已存在
     * @param phoneNumber 手机号
     * @return boolean 通过校验，返回ture,否则返回false
     */
    @Override
    public boolean validateMobilePhoneNum(String phoneNumber) {
        if (regValidatePhoneNum(phoneNumber)){
            int count = mobileRegisterDao.getUserCountByCellphone(phoneNumber);
            if (count > 0){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * @function 校验用户输入的注册授权码是否正确
     * @param phoneNumber 手机号
     * @param vCode 授权码
     * @return boolean 通过校验，返回ture,否则返回false
     */
    @Override
    public boolean validateVCode(String phoneNumber, String vCode) {
        if ("".equals(phoneNumber) || phoneNumber == null){
            return false;
        } else if(!regValidatePhoneNum(phoneNumber)){
            return false;
        } else {
            int count = mobileRegisterDao.getAuthInfo(phoneNumber, vCode, CommonConstants.AuthInfoStatus.INACTIVE);
            if (count > 0){
                return true;
            }
            return false;
        }
    }

    /**
     * @function 校验推荐人是否存在
     * @param referrer 推荐人
     * @return boolean 通过校验，返回ture,否则返回false
     */
    @Override
    public boolean validateReferrer(String referrer) {
        int count = mobileRegisterDao.getUserCountByUserName(referrer);
        if (count == 1){
            return true;
        }
        return false;
    }

    /**
     * @function 正则校验手机号是否符合要求
     * @param phoneNumber 手机号
     * @return 通过校验返回true，否则返回false
     */
    public boolean regValidatePhoneNum(String phoneNumber){
        if(phoneNumber==null || "".equals(phoneNumber)){
            return false;
        }
        String regStr = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(phoneNumber);
        matcher.matches();
        return matcher.matches();
    }
    /***************************setter注入方法****************************/
    public void setMobileRegisterDao(IMobileRegisterDao mobileRegisterDao) {
        this.mobileRegisterDao = mobileRegisterDao;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setMessageBO(MessageBO messageBO) {
        this.messageBO = messageBO;
    }

    public void setUserBO(UserBO userBO) {
        this.userBO = userBO;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
