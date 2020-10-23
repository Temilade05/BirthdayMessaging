package com.demtem.birthday_messaging.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
