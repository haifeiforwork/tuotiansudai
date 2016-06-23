package com.tuotiansudai.service.impl;

import com.tuotiansudai.client.PayWrapperClient;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.model.AccountModel;
import com.tuotiansudai.service.CheckUserBalanceService;
import com.tuotiansudai.util.SendCloudMailUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckUserBalanceServiceImpl implements CheckUserBalanceService {

    private static Logger logger = Logger.getLogger(CheckUserBalanceServiceImpl.class);

    @Autowired
    private PayWrapperClient payWrapperClient;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private SendCloudMailUtil sendCloudMailUtil;

    @Value("#{'${check.user.balance.notify.email}'.split('\\|')}")
    private List<String> notifyEmailAddressList;

    private static final int BATCH_SIZE = 10000;

    public void checkUserBalance() {
        logger.debug("start checkUserBalance.");

        int totalCount = accountMapper.findTotalAccountCount();

        int startIndex = 0;

        Map<String, Object> resultMap = new HashMap<>();
        List<String> mismatchUserList = new ArrayList<>();
        List<String> failUserList = new ArrayList<>();

        resultMap.put("startTime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        while (startIndex < totalCount) {
            logger.debug("checkUserBalance, run to index: " + startIndex);
            List<AccountModel> accountModelList = accountMapper.findAccountWithBalance(startIndex, BATCH_SIZE);

            for (AccountModel account : accountModelList) {
                Map<String, String> balanceMap = payWrapperClient.getUserBalance(account.getLoginName());
                if (balanceMap == null) {
                    logger.debug("check user balance for user " + account.getLoginName() + " fail. skip it.");
                    failUserList.add(account.getLoginName());
                    continue;
                }
                long balance = Long.parseLong(balanceMap.get("balance"));
                if(balance != account.getBalance()) {
                    mismatchUserList.add(account.getLoginName() + "-" + account.getBalance() + "-" + balance);
                }
            }
            startIndex += BATCH_SIZE;
        }
        resultMap.put("failList", failUserList);
        resultMap.put("userList", mismatchUserList);
        resultMap.put("endTime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        sendCloudMailUtil.sendUserBalanceCheckingResult(notifyEmailAddressList, resultMap);

        logger.debug("end checkUserBalance.");
    }
}
