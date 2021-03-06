package com.tuotiansudai.api.util;


import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.tuotiansudai.repository.model.UserBillOperationType;
import com.tuotiansudai.util.AmountConverter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CommonUtils {

    public static String encryptBankCardNo(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            return "";
        }

        if (cardNo.length() > 4) {

            cardNo = cardNo.substring(0, 4) + "***" + cardNo.substring(cardNo.length() - 4);

        }
        return cardNo;
    }

    public static String convertRealMoneyByType(long amount, UserBillOperationType type) {

        if (UserBillOperationType.TI_BALANCE.equals(type)) {
            return "+" + AmountConverter.convertCentToString(amount);
        } else if (UserBillOperationType.TO_BALANCE.equals(type) || UserBillOperationType.TO_FREEZE.equals(type)) {
            return "-" + AmountConverter.convertCentToString(amount);
        }
        return "" + AmountConverter.convertCentToString(amount);
    }

    public static String mapToFormData(Map<String, String> map) throws UnsupportedEncodingException {
        Map<String, String> mapCopy = Maps.newHashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String encodeValue = URLEncoder.encode(entry.getValue(), "UTF-8");
            mapCopy.put(entry.getKey(), encodeValue);
        }

        return Joiner.on("&").withKeyValueSeparator("=").join(mapCopy);
    }

    public static String calculatorInvestBeginSeconds(Date investBeginTime) {
        if (investBeginTime == null) {
            return "0";
        }
        Long time = (investBeginTime.getTime() - System
                .currentTimeMillis()) / 1000;
        if (time < 0) {
            return "0";
        }
        return time.toString();

    }

    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip.split(",")[0];
    }
}
