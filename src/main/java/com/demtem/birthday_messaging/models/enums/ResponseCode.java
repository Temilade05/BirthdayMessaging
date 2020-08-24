package com.demtem.birthday_messaging.models.enums;

public enum ResponseCode {

    NotFound("V44"),
    SuccessFul("00");

    private String value;

    public String getValue() {
        return this.value;
    }

    ResponseCode(String value) {
        this.value = value;
    }
}
