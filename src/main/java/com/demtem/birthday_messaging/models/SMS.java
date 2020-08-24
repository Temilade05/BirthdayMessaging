package com.demtem.birthday_messaging.models;

import lombok.Data;

@Data
public class SMS {

    private String to;
    private String message;

    @Override
    public String toString() {
        return "SMS{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
