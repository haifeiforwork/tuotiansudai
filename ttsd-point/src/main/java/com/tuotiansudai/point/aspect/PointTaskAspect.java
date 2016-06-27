package com.tuotiansudai.point.aspect;

import com.tuotiansudai.dto.AgreementBusinessType;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.RegisterAccountDto;
import com.tuotiansudai.point.repository.model.PointTask;
import com.tuotiansudai.point.service.PointService;
import com.tuotiansudai.point.service.PointTaskService;
import com.tuotiansudai.repository.mapper.BankCardMapper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.RechargeMapper;
import com.tuotiansudai.repository.model.BankCardModel;
import com.tuotiansudai.repository.model.InvestModel;
import com.tuotiansudai.repository.model.RechargeModel;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
public class PointTaskAspect {

    static Logger logger = Logger.getLogger(PointTaskAspect.class);

    @Autowired
    private PointService pointService;

    @Autowired
    private PointTaskService pointTaskService;

    @Autowired
    private RechargeMapper rechargeMapper;

    @Autowired
    private BankCardMapper bankCardMapper;

    @AfterReturning(value = "execution(* *..RegisterService.register(..))", returning = "returnValue")
    public void afterReturningRegisterAccount(JoinPoint joinPoint, BaseDto<PayDataDto> returnValue) {
        logger.debug("after returning register account, point task aspect starting...");

        if (returnValue.getData().getStatus()) {
            RegisterAccountDto registerAccountDto = (RegisterAccountDto) joinPoint.getArgs()[0];
            pointTaskService.completeTask(PointTask.REGISTER, registerAccountDto.getLoginName());
        }

        logger.debug("after returning register account, point task aspect completed");
    }

    @AfterReturning(value = "execution(* *..InvestService.investSuccess(..))")
    public void afterReturningInvestSuccess(JoinPoint joinPoint) {
        logger.debug("after returning invest, point task aspect starting...");

        InvestModel investModel = (InvestModel) joinPoint.getArgs()[0];
        pointTaskService.completeTask(PointTask.FIRST_INVEST, investModel.getLoginName());
        pointService.obtainPointInvest(investModel);

        pointTaskService.completeTask(PointTask.FIRST_TURN_ON_NO_PASSWORD_INVEST, investModel.getLoginName());

        pointTaskService.completeTask(PointTask.FIRST_TURN_ON_AUTO_INVEST, investModel.getLoginName());

        logger.debug("after returning invest, point task aspect completed");
    }

    @SuppressWarnings(value = "unchecked")
    @AfterReturning(value = "execution(* *..RechargeService.rechargeCallback(..))")
    public void afterReturningRechargeCallback(JoinPoint joinPoint) {
        logger.debug("after returning recharge, point task aspect starting...");

        Map<String, String> paramsMap = (Map<String, String>) joinPoint.getArgs()[0];
        RechargeModel rechargeModel = rechargeMapper.findById(Long.parseLong(paramsMap.get("order_id")));
        pointTaskService.completeTask(PointTask.FIRST_RECHARGE, rechargeModel.getLoginName());

        logger.debug("after returning recharge, point task aspect completed");
    }

    @SuppressWarnings(value = "unchecked")
    @AfterReturning(value = "execution(* *..BindBankCardService.bindBankCardCallback(..))")
    public void afterReturningBindBankCardCallback(JoinPoint joinPoint) {
        logger.debug("after returning bind card, point task aspect starting...");

        Map<String, String> paramsMap = (Map<String, String>) joinPoint.getArgs()[0];
        BankCardModel bankCardModel = bankCardMapper.findById(Long.parseLong(paramsMap.get("order_id")));
        pointTaskService.completeTask(PointTask.BIND_BANK_CARD, bankCardModel.getLoginName());

        logger.debug("after returning bind card, point task aspect completed");
    }

    @SuppressWarnings(value = "unchecked")
    @AfterReturning(value = "execution(* *..AgreementService.postAgreementCallback(..))")
    public void afterReturningNoPasswordInvestAgreementCallback(JoinPoint joinPoint) {
        logger.debug("after returning agreement, point task aspect starting...");

        String loginName = (String) joinPoint.getArgs()[0];
        AgreementBusinessType agreementBusinessType = (AgreementBusinessType) joinPoint.getArgs()[1];

        if (AgreementBusinessType.NO_PASSWORD_INVEST == agreementBusinessType) {
            pointTaskService.completeTask(PointTask.FIRST_TURN_ON_NO_PASSWORD_INVEST, loginName);
        }

        logger.debug("after returning agreement, point task aspect completed");
    }

    @SuppressWarnings(value = "unchecked")
    @AfterReturning(value = "execution(* *..InvestService.switchNoPasswordInvest(..))")
    public void afterReturningTurnOnNoPasswordInvestCallback(JoinPoint joinPoint) {
        logger.debug("after returning turn on no password invest, point task aspect starting...");

        String loginName = (String) joinPoint.getArgs()[0];
        boolean isTurn = (boolean) joinPoint.getArgs()[1];

        if (isTurn) {
            pointTaskService.completeTask(PointTask.FIRST_TURN_ON_NO_PASSWORD_INVEST, loginName);
        }

        logger.debug("after returning turn on no password invest, point task aspect completed");
    }

    @SuppressWarnings(value = "unchecked")
    @AfterReturning(value = "execution(* *..InvestService.turnOnAutoInvest(..))")
    public void afterReturningTurnOnAutoInvestCallback(JoinPoint joinPoint) {
        logger.debug("after returning turn on auto invest, point task aspect starting...");

        String loginName = (String) joinPoint.getArgs()[0];

        pointTaskService.completeTask(PointTask.FIRST_TURN_ON_AUTO_INVEST, loginName);

        logger.debug("after returning turn on auto invest, point task aspect completed");
    }
}
