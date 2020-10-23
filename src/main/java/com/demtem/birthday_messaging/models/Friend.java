package com.demtem.birthday_messaging.models;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Friend {

    @Id
    private ObjectId _id;

    private ObjectId userId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String eMailAddress;

    private Message message;

    private Date dateOfBirth;

    public String get_id(){
        return _id.toHexString();
    }

    public String getUserId() { return userId.toHexString(); }

    @Override
    public String toString() {
        return "Friend{" +
                "_id=" + _id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", eMailAddress='" + eMailAddress + '\'' +
                ", message=" + message +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
