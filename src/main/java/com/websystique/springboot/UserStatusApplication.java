package com.websystique.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.websystique.springboot"})
public class UserStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserStatusApplication.class, args);
	}
}
