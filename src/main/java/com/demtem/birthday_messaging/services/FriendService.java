package com.demtem.birthday_messaging.services;

import com.demtem.birthday_messaging.exceptions.DatabaseException;
import com.demtem.birthday_messaging.exceptions.EntityNotFoundException;
import com.demtem.birthday_messaging.models.Friend;
import com.demtem.birthday_messaging.models.responses.Response;
import com.demtem.birthday_messaging.repositories.FriendRepository;
import com.demtem.birthday_messaging.services.contracts.IService;
import com.demtem.birthday_messaging.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class FriendService implements IService<Friend> {

    @Autowired
    private FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Response<Friend> createT(Friend friend) {
        log.info("Started creating new friend");

        ResponseUtil<Friend> responseUtil = new ResponseUtil<>();
        Friend newFriend;

        friend.set_id(ObjectId.get());

        try {
            newFriend = friendRepository.save(friend);
        } catch (Exception  e){
            log.error("Error", e);
            throw new DatabaseException("Error occurred while  creating new friend");
        }

        log.info("Created friend successfully");
        return responseUtil.successfulResponse(Collections.singletonList(newFriend));
    }

    @Override
    public Response<Friend> readAllTs() {

        log.info("Started getting all  friends");

        ResponseUtil<Friend> responseUtil = new ResponseUtil<>();
        List<Friend> friends;

        try {
            friends = friendRepository.findAll();
        }catch (Exception e){
            log.error("Error ", e);
            throw new DatabaseException("Error occurred while getting all friends.");
        }

        log.info("Got all friends successfully.");

        return responseUtil.successfulResponse(friends);
    }

    @Override
    public Response<Friend> readTById(ObjectId id) {
        log.info("Started getting friend for id " + id.toHexString());

        ResponseUtil<Friend> responseUtil = new ResponseUtil<>();

        Optional<Friend> friend;

        try {
            friend = friendRepository.findById(id);
        } catch (Exception e) {
            log.error("Error ", e);

            throw new DatabaseException("Error occurred while getting friend." );
        }

        if (friend.isEmpty()){
            throw new EntityNotFoundException("Friend", String.valueOf(id));
        }

        log.info("Got friend "  + friend.get().toString() + " successfully.");
        return responseUtil.successfulResponse(Collections.singletonList(friend.get()));
    }

    @Override
    public Response<Friend> updateT(Friend friend) {

        log.info("Started updating friend.");

        ResponseUtil<Friend> responseUtil = new ResponseUtil<>();

        Friend updatedFriend = new Friend();
        updatedFriend.set_id(new ObjectId(friend.get_id()));

        try {
            updatedFriend = friendRepository.save(friend);
        } catch (Exception e){
            log.error("Error ", e);
            throw new DatabaseException("Error occurred while updating friend");
        }

        log.info("Friend updated successfully");

        return responseUtil.successfulResponse(Collections.singletonList(updatedFriend));
    }

    @Override
    public Response<Friend> deleteT(ObjectId id) {
        log.info("Started deleting friend for id " + id);

        ResponseUtil<Friend> responseUtil = new ResponseUtil<>();

        try {
            friendRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Friend", String.valueOf(id));
        } catch (Exception e) {
            log.error("Error ", e);
            throw new DatabaseException("Error occurred while deleting friend");
        }

        log.info("Successfully deleted friend");
        return responseUtil.successfulResponse(null);
    }

}
