package com.tuotiansudai.repository.model;

import com.tuotiansudai.dto.LoanApplicationDto;

import java.io.Serializable;
import java.util.Date;

public class LoanApplicationModel implements Serializable {
    private long id;
    private String loginName;
    private String mobile;
    private String userName;
    private LoanApplicationRegion region;
    private int amount;
    private int period;
    private PledgeType pledgeType;
    private String pledgeInfo;
    private String comment;
    private Date createdTime;
    private String updatedBy;
    private Date updatedTime;

    private String identityNumber;
    private String address;
    private short  age;
    //是否结婚
    private boolean isMarried;
    //是否有信用报告
    private boolean haveCreditReport;
    //工作职位
    private String workPosition;
    //蚂蚁积分
    private int  sesameCredit;
    //家庭年收入(万)
    private int  homeIncome;
    //借款用途
    private String loanUsage;
    //其他资产
    private String elsePledge;
    //性别
    private String sex;


    public LoanApplicationModel() {
    }

    public LoanApplicationModel(LoanApplicationDto loanApplicationDto) {
//        id
        this.loginName = loanApplicationDto.getLoginName();
        this.region = loanApplicationDto.getRegion();
        this.amount = loanApplicationDto.getAmount();
        this.period = loanApplicationDto.getPeriod();
        this.pledgeType = loanApplicationDto.getPledgeType();
        this.pledgeInfo = loanApplicationDto.getPledgeInfo();
//        comment
        this.createdTime = new Date();
        this.updatedBy = loanApplicationDto.getLoginName();
        this.updatedTime = new Date();
        //合规审查新增字段
        this.identityNumber=loanApplicationDto.getIdentityNumber();
        this.address=loanApplicationDto.getAddress();
        this.age=loanApplicationDto.getAge();
        this.isMarried=loanApplicationDto.getIsMarried();
        this.haveCreditReport=loanApplicationDto.getHaveCreditReport();
        this.workPosition=loanApplicationDto.getWorkPosition();
        this.sesameCredit=loanApplicationDto.getSesameCredit();
        this.homeIncome=loanApplicationDto.getHomeIncome();
        this.loanUsage=loanApplicationDto.getLoanUsage();
        this.elsePledge=loanApplicationDto.getElsePledge();
        this.sex=loanApplicationDto.getSex();
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LoanApplicationRegion getRegion() {
        return region;
    }

    public void setRegion(LoanApplicationRegion region) {
        this.region = region;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public PledgeType getPledgeType() {
        return pledgeType;
    }

    public void setPledgeType(PledgeType pledgeType) {
        this.pledgeType = pledgeType;
    }

    public String getPledgeInfo() {
        return pledgeInfo;
    }

    public void setPledgeInfo(String pledgeInfo) {
        this.pledgeInfo = pledgeInfo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public boolean getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(boolean married) {
        isMarried = married;
    }

    public boolean getHaveCreditReport() {
        return haveCreditReport;
    }

    public void setHaveCreditReport(boolean haveCreditReport) {
        this.haveCreditReport = haveCreditReport;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public int getSesameCredit() {
        return sesameCredit;
    }

    public void setSesameCredit(int sesameCredit) {
        this.sesameCredit = sesameCredit;
    }

    public int getHomeIncome() {
        return homeIncome;
    }

    public void setHomeIncome(int homeIncome) {
        this.homeIncome = homeIncome;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getElsePledge() {
        return elsePledge;
    }

    public void setElsePledge(String elsePledge) {
        this.elsePledge = elsePledge;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
