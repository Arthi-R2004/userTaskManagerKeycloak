package com.example.UserTaskManagerKeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication(exclude = { FlywayAutoConfiguration.class })
public class UserTaskManagerKeycloakApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserTaskManagerKeycloakApplication.class, args);
	}

}
