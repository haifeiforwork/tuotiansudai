package com.tuotiansudai.repository.model;

import java.io.Serializable;
import java.util.Date;

public class AccountModel implements Serializable{

    private static final long serialVersionUID = -1887375479975954346L;

    private long id;

    private String loginName;

    private String userName;

    private String identityNumber;

    private String payUserId;

    private String payAccountId;

    private long balance;

    private long freeze;

    private long point;

    private Date registerTime;

    private boolean autoInvest;

    private boolean autoRepay;

    private boolean noPasswordInvest;

    private long membershipPoint;

    private String anxinUserId;

    private boolean isSkipAuth;

    private String projectCode;

    public AccountModel() {
    }

    public AccountModel(String loginName, String userName, String identityNumber, String payUserId, String payAccountId, Date registerTime) {
        this.loginName = loginName;
        this.userName = userName;
        this.identityNumber = identityNumber;
        this.payUserId = payUserId;
        this.payAccountId = payAccountId;
        this.registerTime = registerTime;
    }

    public long getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getUserName() {
        return userName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public String getPayUserId() {
        return payUserId;
    }

    public String getPayAccountId() {
        return payAccountId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getFreeze() {
        return freeze;
    }

    public void setFreeze(long freeze) {
        this.freeze = freeze;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public boolean isAutoInvest() {
        return autoInvest;
    }

    public void setAutoInvest(boolean autoInvest) {
        this.autoInvest = autoInvest;
    }

    public boolean isAutoRepay() {
        return autoRepay;
    }

    public void setAutoRepay(boolean autoRepay) {
        this.autoRepay = autoRepay;
    }

    public boolean isNoPasswordInvest() {return noPasswordInvest;}

    public void setNoPasswordInvest(boolean noPasswordInvest) {
        this.noPasswordInvest = noPasswordInvest;
    }

    public long getMembershipPoint() {
        return membershipPoint;
    }

    public void setMembershipPoint(long membershipPoint) {
        this.membershipPoint = membershipPoint;
    }

    public String getAnxinUserId() {
        return anxinUserId;
    }

    public void setAnxinUserId(String anxinUserId) {
        this.anxinUserId = anxinUserId;
    }

    public boolean isSkipAuth() {
        return isSkipAuth;
    }

    public void setIsSkipAuth(boolean isSkipAuth) {
        this.isSkipAuth = isSkipAuth;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
