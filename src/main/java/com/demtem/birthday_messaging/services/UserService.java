package com.demtem.birthday_messaging.services;

import com.demtem.birthday_messaging.models.Friend;
import com.demtem.birthday_messaging.models.Role;
import com.demtem.birthday_messaging.models.User;
import com.demtem.birthday_messaging.models.enums.RoleName;
import com.demtem.birthday_messaging.models.responses.Response;
import com.demtem.birthday_messaging.repositories.FriendRepository;
import com.demtem.birthday_messaging.repositories.UserRepository;
import com.demtem.birthday_messaging.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response<User> createAdminUser(String email){

        log.info("User with email " + email + " is being made an admin.");

        Optional<User> userOptional = userRepository.findByEmail(email);

        User user = new User();

        if(userOptional.isPresent()) {
            user = userOptional.get();
        }

        List<Role> roles = user.getRoles();

        if (roles.contains(RoleName.ROLE_ADMIN)){
            return null;
        }

        roles.add(new Role(RoleName.ROLE_ADMIN));
        user.setRoles(roles);

        return new ResponseUtil<User>().successfulResponse(Collections.singletonList(userRepository.save(user)));
    }

}