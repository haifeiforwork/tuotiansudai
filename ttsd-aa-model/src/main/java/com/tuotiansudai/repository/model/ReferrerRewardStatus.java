package com.tuotiansudai.repository.model;

public enum ReferrerRewardStatus {

    SUCCESS("推荐奖励发放成功"),

    FAILURE("推荐奖励发放失败"),

    NO_ACCOUNT("推荐人未实名认证"),

    FORBIDDEN("资产业务员和资产系用户线下发放奖励");

    private final String description;

    ReferrerRewardStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
