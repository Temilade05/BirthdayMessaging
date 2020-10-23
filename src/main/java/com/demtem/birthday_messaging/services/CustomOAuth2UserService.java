package com.demtem.birthday_messaging.services;

import com.demtem.birthday_messaging.exceptions.EntityNotFoundException;
import com.demtem.birthday_messaging.models.User;
import com.demtem.birthday_messaging.models.UserPrincipal;
import com.demtem.birthday_messaging.models.enums.RoleName;
import com.demtem.birthday_messaging.models.Role;
import com.demtem.birthday_messaging.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("Started loading a new user.");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(oAuth2User);
        } catch (AuthenticationException ex) {
            log.error("An error occurred while processing the user.");
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {

        log.info("Processing user...");

        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();

            user = updateExistingUser(user, oAuth2UserInfo);

            log.info("Processed and updated user successfully.");

        } else {
            user = registerNewUser(oAuth2UserInfo);

            log.info("Processed and registered new user successfully.");
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProviderId(oAuth2UserInfo.getId());
        user.setFirstName(oAuth2UserInfo.getFirstName());
        user.setLastName(oAuth2UserInfo.getLastName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setRoles(Collections.singletonList(new Role(RoleName.ROLE_USER)));
        user.setDateJoined(new Date());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFirstName(oAuth2UserInfo.getFirstName());
        existingUser.setLastName(oAuth2UserInfo.getLastName());
        return userRepository.save(existingUser);
    }


    @Transactional
    public UserDetails loadUserById(ObjectId _id) {
        User user = userRepository.findById(_id).orElseThrow(
                () -> new EntityNotFoundException("id", _id.toHexString())
        );

        return UserPrincipal.create(user);
    }
}