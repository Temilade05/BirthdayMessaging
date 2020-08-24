package com.demtem.birthday_messaging.controllers;

import com.demtem.birthday_messaging.models.Friend;
import com.demtem.birthday_messaging.models.responses.Response;
import com.demtem.birthday_messaging.services.FriendService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/friend")
public class FriendController {

    @Autowired
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("")
    public ResponseEntity<Response<Friend>> getAllFriends() {
        Response<Friend> friendResponse = friendService.readAllTs();
        return new ResponseEntity<>(friendResponse, HttpStatus.valueOf(Integer.parseInt(friendResponse.getStatusCode())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Friend>> getFriend(@PathVariable ObjectId id) {
        Response<Friend> friendResponse = friendService.readTById(id);
        return new ResponseEntity<>(friendResponse, HttpStatus.valueOf(Integer.parseInt(friendResponse.getStatusCode())));
    }

    @PostMapping("/add")
    public ResponseEntity<Response<Friend>> addFriend(@RequestBody Friend friend) {
        Response<Friend> friendResponse = friendService.createT(friend);
        return new ResponseEntity<>(friendResponse, HttpStatus.valueOf(Integer.parseInt(friendResponse.getStatusCode())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Friend>> updateFriend(@RequestBody Friend friend) {
        Response<Friend> friendResponse = friendService.updateT(friend);
        return new ResponseEntity<>(friendResponse, HttpStatus.valueOf(Integer.parseInt(friendResponse.getStatusCode())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Friend>> deleteFriend(@PathVariable ObjectId id) {
        Response<Friend> friendResponse = friendService.deleteT(id);
        return new ResponseEntity<>(friendResponse, HttpStatus.valueOf(Integer.parseInt(friendResponse.getStatusCode())));
    }
}
