package com.example.UserTaskManagerKeycloak.Config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak() {
        try {
            Keycloak keycloak= KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080") // Keycloak URL
                    .realm("try2")                    // Realm with admin access
                    .clientId("spring-boot-cli")// Use "admin-cli" or a custom client with proper roles
                    .clientSecret("3BMPeSuXH3232GYucdvmCRCEo6lUQQi6")
                    .grantType(OAuth2Constants.PASSWORD)
                    .username("try2-admin")                  // Admin username
                    .password("admin123")                  // Admin password
                    .build();

            keycloak.tokenManager().grantToken();
            System.out.println("Token granted");
            return keycloak;
        } catch (Exception e) {
            System.err.println("Error during token request:"+e.getMessage());
            throw new RuntimeException(e);
        }
    }


}

