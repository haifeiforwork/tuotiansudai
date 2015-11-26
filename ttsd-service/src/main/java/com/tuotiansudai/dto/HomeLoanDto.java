package com.tuotiansudai.dto;

import com.tuotiansudai.repository.model.ActivityType;
import com.tuotiansudai.repository.model.LoanStatus;

import java.math.BigDecimal;

public class HomeLoanDto {

    private long id;

    private String name;

    private String activityType;

    private double baseRate;

    private double activityRate;

    private int periods;

    private String amount;

    private String progress;

    private String status;

    public HomeLoanDto(long loanId, String name, ActivityType activityType, double baseRate, double activityRate, int periods, long amount, long investAmount, LoanStatus status) {
        this.id = loanId;
        this.name = name;
        this.activityType = activityType.name();
        this.baseRate = new BigDecimal(String.valueOf(baseRate)).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
        if (activityRate > 0) {
            this.activityRate = new BigDecimal(String.valueOf(activityRate)).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
        }
        this.periods = periods;
        this.amount = new BigDecimal(amount).toString();
        this.progress = String.valueOf(new BigDecimal(investAmount).divide(new BigDecimal(amount), 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).intValue());
        this.status = status.name();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public double getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(double activityRate) {
        this.activityRate = activityRate;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
