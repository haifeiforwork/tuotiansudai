package com.tuotiansudai.enums;

public enum TransferType {

    FREEZE("冻结"),

    UNFREEZE("解冻"),

    TRANSFER_IN_BALANCE("余额转入"),

    TRANSFER_OUT_BALANCE("余额转出"),

    TRANSFER_OUT_FREEZE("冻结金额转出");

    private String description;

    TransferType(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
