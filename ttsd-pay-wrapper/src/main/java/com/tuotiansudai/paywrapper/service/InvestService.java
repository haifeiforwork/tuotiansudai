package com.tuotiansudai.paywrapper.service;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.InvestDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.paywrapper.repository.model.async.callback.InvestNotifyRequestModel;
import com.tuotiansudai.repository.model.AutoInvestPlanModel;
import com.tuotiansudai.repository.model.InvestModel;
import com.tuotiansudai.util.AutoInvestMonthPeriod;

import java.util.List;
import java.util.Map;

public interface InvestService {

    BaseDto<PayFormDataDto> invest(InvestDto dto);

    String investCallback(Map<String, String> paramsMap, String queryString);

    BaseDto<PayDataDto> asyncInvestCallback(long notifyRequestId);

    String overInvestPaybackCallback(Map<String, String> paramsMap, String queryString);

    void autoInvest(long loanId);

    List<AutoInvestPlanModel> findValidPlanByPeriod(AutoInvestMonthPeriod period);

    void processOneCallback(InvestNotifyRequestModel callbackRequestModel);

    void investSuccess(InvestModel investModel);

    BaseDto<PayDataDto> noPasswordInvest(InvestDto dto);

    void sendExperienceInterestInvestSuccess(long investId);

    String experienceInterestCallback(Map<String, String> paramsMap, String originalQueryString);

}
