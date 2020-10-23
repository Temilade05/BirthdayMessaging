package com.demtem.birthday_messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BirthdayMessagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirthdayMessagingApplication.class, args);
	}

}
