package com.demtem.birthday_messaging.repositories;

import com.demtem.birthday_messaging.models.Friend;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FriendRepository extends MongoRepository<Friend, ObjectId> {
}
