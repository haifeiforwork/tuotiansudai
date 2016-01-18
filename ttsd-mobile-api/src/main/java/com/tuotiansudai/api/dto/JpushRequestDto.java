package com.tuotiansudai.api.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class JpushRequestDto extends BaseParamDto{
    @NotEmpty(message = "0023")
    private String jpushId;

    public String getJpushId() {
        return jpushId;
    }

    public void setJpushId(String jpushId) {
        this.jpushId = jpushId;
    }
}
