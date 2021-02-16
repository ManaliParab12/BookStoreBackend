package com.bridgelabz.onlinebookstore.configuration;

import java.security.Timestamp;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.common.base.Function;

@Configuration
public class SecurityConfiguration {
	
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Profile("dev")
	@Bean
	public void run() {
		System.out.println("Dev environment Running");
	}
}
