package com.demtem.birthday_messaging.models;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Friend {

    @Id
    private ObjectId _id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Message message;

    private Date dateOfBirth;

    public String get_id(){
        return _id.toHexString();
    }
}
