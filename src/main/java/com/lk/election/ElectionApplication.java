package com.lk.election;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ElectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectionApplication.class, args);
	}

}
