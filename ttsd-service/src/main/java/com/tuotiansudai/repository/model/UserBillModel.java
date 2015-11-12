package com.tuotiansudai.repository.model;

import java.io.Serializable;
import java.util.Date;

public class UserBillModel implements Serializable {

    private long id;

    private String loginName;

    private long orderId;

    private long amount;

    private long balance;

    private long freeze;

    private UserBillBusinessType businessType;

    private UserBillOperationType operationType;

    private String operatorLoginName;

    private String description;

    private Date createdTime = new Date();

    public UserBillModel() {
    }

    public UserBillModel(String loginName, long orderId, long amount, long balance, long freeze, UserBillBusinessType businessType, UserBillOperationType operationType) {
        this.loginName = loginName;
        this.orderId = orderId;
        this.amount = amount;
        this.balance = balance;
        this.freeze = freeze;
        this.businessType = businessType;
        this.operationType = operationType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

    public UserBillBusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(UserBillBusinessType businessType) {
        this.businessType = businessType;
    }

    public UserBillOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(UserBillOperationType operationType) {
        this.operationType = operationType;
    }

    public String getOperatorLoginName() {
        return operatorLoginName;
    }

    public void setOperatorLoginName(String operatorLoginName) {
        this.operatorLoginName = operatorLoginName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
