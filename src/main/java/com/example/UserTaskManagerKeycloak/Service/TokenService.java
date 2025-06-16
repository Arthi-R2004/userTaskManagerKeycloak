package com.example.UserTaskManagerKeycloak.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class TokenService {

    @Value("${keycloak.realm.name}")
    private String realm;

    @Value("${keycloak.server.url}")
    private String keycloakBaseUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    public String getAccessToken(String username, String password) {
        String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(
                Map.of(
                        "grant_type", "password",
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "username", username,
                        "password", password
                ),
                headers
        );

        ResponseEntity<Map> response = new RestTemplate().postForEntity(tokenUrl, request, Map.class);
        return (String) response.getBody().get("access_token");
    }
}

