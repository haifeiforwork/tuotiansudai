package com.tuotiansudai.fudian.message;


public class BankRegisterMessage {

    private String loginName;

    private String mobile;

    private String identityCode;

    private String realName;

    private String bankAccountNo;

    private String bankUserName;

    private String bankOrderDate;

    private String bankOrderNo;

    public BankRegisterMessage() {
    }

    public BankRegisterMessage(String loginName, String mobile, String identityCode, String realName, String bankAccountNo, String bankUserName, String bankOrderDate, String bankOrderNo) {
        this.loginName = loginName;
        this.mobile = mobile;
        this.identityCode = identityCode;
        this.realName = realName;
        this.bankAccountNo = bankAccountNo;
        this.bankUserName = bankUserName;
        this.bankOrderDate = bankOrderDate;
        this.bankOrderNo = bankOrderNo;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public String getRealName() {
        return realName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public String getBankOrderDate() {
        return bankOrderDate;
    }

    public String getBankOrderNo() {
        return bankOrderNo;
    }
}
