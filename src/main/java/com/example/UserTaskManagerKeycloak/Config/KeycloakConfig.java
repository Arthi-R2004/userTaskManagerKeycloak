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
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080") // Keycloak URL
                .realm("springboot-demo")                    // Realm with admin access
                .clientId("springboot-client")              // Use "admin-cli" or a custom client with proper roles
                .grantType(OAuth2Constants.PASSWORD)
                .username("realm-admin")                  // Admin username
                .password("admin123")                  // Admin password
                .build();
    }


}

