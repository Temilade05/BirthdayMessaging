package com.demtem.birthday_messaging.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class eMail {

    private String to;
    private String subject;
    private String message;
}
