package com.demtem.birthday_messaging.services;

import com.demtem.birthday_messaging.models.Friend;
import com.demtem.birthday_messaging.models.SMS;
import com.demtem.birthday_messaging.models.eMail;
import com.demtem.birthday_messaging.utils.CustomAggregationOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Component
@Slf4j
public class ScheduleService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SMSService smsService;

    @Autowired
    private eMailService emailService;

    public ScheduleService(MongoTemplate mongoTemplate, SMSService smsService, eMailService emailService) {
        this.mongoTemplate = mongoTemplate;
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void ScheduleMessagesWithCron(){
        log.info("Getting birthdays.");

        List<Friend> friends = getBirthdays();
        log.info(friends.toString());

        log.info("Sending messages.");

        for (Friend friend: friends){
            SMS sms = new SMS(friend.getPhoneNumber(), friend.getMessage().getContent());
            smsService.send(sms);

            eMail email = new eMail(friend.getEMailAddress(), "birthday message", friend.getMessage().getContent());
            emailService.sendSimpleMessage(email);
        }
    }


    private List<Friend> getBirthdays(){

        String projectQuery = "{$project: " +
                "{ \"todayDayOfMonth\" : { \"$dayOfMonth\" : new Date()}, " +
                "\"birthDayOfMonth\" : { \"$dayOfMonth\" : \"$dateOfBirth\"}, " +
                "\"todayMonth\" : { \"$month\" : new  Date()}, " +
                "\"birthDayMonth\" : { \"$month\" : \"$dateOfBirth\"}," +
                "\"firstName\": 1,\n" +
                "\"lastName\": 1,\n" +
                "\"message\": 1,\n" +
                "\"phoneNumber\": 1,\n" +
                "\"userId\": 1}} ";

        String matchQuery = "{$match: {" +
                "$expr:{" +
                "  $and: [" +
                "    {$eq: [\"$todayDayOfMonth\", \"$birthDayOfMonth\"]}," +
                "    {$eq: [\"$todayMonth\", \"$birthDayMonth\"]}" +
                "    ]" +
                "}}}";

        Aggregation aggregation = newAggregation(
                new CustomAggregationOperation(projectQuery),
                new CustomAggregationOperation(matchQuery)
        );

        AggregationResults<Friend> friends =  mongoTemplate.aggregate(aggregation, "friend", Friend.class);

        return friends.getMappedResults();
    }
}
