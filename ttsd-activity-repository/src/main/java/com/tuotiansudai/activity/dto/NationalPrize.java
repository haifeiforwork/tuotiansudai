package com.tuotiansudai.activity.dto;

public enum NationalPrize {
    MEMBERSHIP_V5("一个月V5会员体验"),
    RED_ENVELOPE_15("15元投资红包"),
    RED_ENVELOPE_50("50元投资红包"),
    TELEPHONE_FARE_10("10元话费"),
    IQIYI_MEMBERSHIP("爱奇艺会员");

    String description;

    NationalPrize(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
