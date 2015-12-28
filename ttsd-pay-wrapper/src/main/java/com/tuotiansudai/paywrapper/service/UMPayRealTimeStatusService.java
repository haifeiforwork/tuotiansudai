package com.tuotiansudai.paywrapper.service;

import java.util.Map;

public interface UMPayRealTimeStatusService {

    Map<String, String> getUserStatus(String loginName);

    Map<String, String> getPlatformStatus();

    Map<String, String> getLoanStatus(long loanId);

    Map<String,String> getTransferStatus(String orderId, String merDate, String businessType);
}
