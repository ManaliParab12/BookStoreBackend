package com.bridgelabz.onlinebookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class OnlineBookStoreApplication {

	public static void main(String[] args) {
		System.out.println("Demo Jenkins");
		SpringApplication.run(OnlineBookStoreApplication.class, args);
	}
}

//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.-aZ-fmWj6R_sG58mKbGaDiUijHH26sOupSsajQ7q548eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.-aZ-fmWj6R_sG58mKbGaDiUijHH26sOupSsajQ7q548