package com.demtem.birthday_messaging.models;

import lombok.Data;

@Data
public class eMail {

    private String to;
    private String subject;
    private String message;
}
