package com.tuotiansudai.api.dto.v1_0;

public class ReferrerInvestListRequestDto extends BaseParamDto{
    private String referrerId;

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(String referrerId) {
        this.referrerId = referrerId;
    }
}
