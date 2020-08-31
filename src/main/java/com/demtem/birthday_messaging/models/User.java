package com.demtem.birthday_messaging.models;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
public class User {

    @Id
    private ObjectId _id;

    private String firstName;
    private String lastName;
    private String email;

    private List<Friend> friends;

    private List<Role> roles;
    private Date dateJoined;

    private String providerId;

    public String get_id(){
        return _id.toHexString();
    }
}
