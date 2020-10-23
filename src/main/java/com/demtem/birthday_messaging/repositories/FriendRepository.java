package com.demtem.birthday_messaging.repositories;

import com.demtem.birthday_messaging.models.Friend;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendRepository extends MongoRepository<Friend, ObjectId> {

    List<Friend> findByUserId(ObjectId userId);
}
