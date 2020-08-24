package com.demtem.birthday_messaging.repositories;

import com.demtem.birthday_messaging.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

}
