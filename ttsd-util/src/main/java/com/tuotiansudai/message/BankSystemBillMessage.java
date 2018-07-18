package com.tuotiansudai.message;

import com.tuotiansudai.enums.SystemBillBusinessType;
import com.tuotiansudai.enums.SystemBillMessageType;

public class BankSystemBillMessage {

    private SystemBillMessageType messageType;

    private String bankOrderNo;

    private String BankOrderDate;

    private long amount;

    private SystemBillBusinessType businessType;

    private String detail;

    public BankSystemBillMessage() {
    }

    public BankSystemBillMessage(SystemBillMessageType messageType, String bankOrderNo, long amount, SystemBillBusinessType businessType, String detail) {
        this.messageType = messageType;
        this.bankOrderNo = bankOrderNo;
        this.amount = amount;
        this.businessType = businessType;
        this.detail = detail;
    }

    public SystemBillMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(SystemBillMessageType messageType) {
        this.messageType = messageType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public SystemBillBusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(SystemBillBusinessType businessType) {
        this.businessType = businessType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBankOrderNo() {
        return bankOrderNo;
    }

    public void setBankOrderNo(String bankOrderNo) {
        this.bankOrderNo = bankOrderNo;
    }

    public String getBankOrderDate() {
        return BankOrderDate;
    }

    public void setBankOrderDate(String bankOrderDate) {
        BankOrderDate = bankOrderDate;
    }
}
