package com.tuotiansudai.api.dto.v2_0;


import com.tuotiansudai.repository.model.PledgeHouseModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PledgeHouseDto implements Serializable {

    @ApiModelProperty(value = "抵押房屋所在地", example = "北京")
    private String pledgeLocation;

    @ApiModelProperty(value = "房屋面积", example = "100")
    private String square;

    @ApiModelProperty(value = "房产估值", example = "100")
    private String estimateAmount;

    @ApiModelProperty(value = "借款公正金额", example = "100")
    private String loanAmount;

    public PledgeHouseDto(PledgeHouseModel pledgeHouseModel) {
        this.pledgeLocation = pledgeHouseModel.getPledgeLocation();
        this.square = pledgeHouseModel.getSquare();
        this.estimateAmount = pledgeHouseModel.getEstimateAmount();
        this.loanAmount = pledgeHouseModel.getLoanAmount();
    }

    public String getPledgeLocation() {
        return pledgeLocation;
    }

    public void setPledgeLocation(String pledgeLocation) {
        this.pledgeLocation = pledgeLocation;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getEstimateAmount() {
        return estimateAmount;
    }

    public void setEstimateAmount(String estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }
}
