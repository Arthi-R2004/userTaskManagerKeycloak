package com.example.UserTaskManagerKeycloak.Service;

import com.example.UserTaskManagerKeycloak.Entity.AppUser;
import com.example.UserTaskManagerKeycloak.Repository.UserRepository;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Keycloak keycloak;

    public void registerUser(String username, String email, String password) {
        // 1. Create user in Keycloak
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));

        Response response = keycloak.realm("springboot-demo").users().create(user);
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // 2. Save user in PostgreSQL
        AppUser dbUser = new AppUser();
        dbUser.setUsername(username);
        dbUser.setEmail(email);
        dbUser.setKeycloakId(userId);
        userRepository.save(dbUser);
    }
}
