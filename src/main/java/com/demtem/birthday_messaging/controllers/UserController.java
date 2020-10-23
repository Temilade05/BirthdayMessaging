package com.demtem.birthday_messaging.controllers;

import com.demtem.birthday_messaging.models.User;
import com.demtem.birthday_messaging.models.responses.Response;
import com.demtem.birthday_messaging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-admin")
    public ResponseEntity<Response<User>> createAdminUser(@RequestBody String emailJSON) throws JSONException {

        JSONObject jsonString = new JSONObject(emailJSON);
        String emailString = jsonString.getString("email");

        Response<User> userResponse  = userService.createAdminUser(emailString);
        return new ResponseEntity<>(userResponse, HttpStatus.valueOf(Integer.parseInt(userResponse.getStatusCode())));
    }
}
