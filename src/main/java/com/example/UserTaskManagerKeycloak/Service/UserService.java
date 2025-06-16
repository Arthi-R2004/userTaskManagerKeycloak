package com.example.UserTaskManagerKeycloak.Service;

import com.example.UserTaskManagerKeycloak.Dto.UserRegistrationDto;
import com.example.UserTaskManagerKeycloak.Entity.AppUser;
import com.example.UserTaskManagerKeycloak.Repository.UserRepository;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private ModelMapper modelMapper;

    public AppUser convertToAppUser(UserRegistrationDto dto, String keycloakId) {
        AppUser user = modelMapper.map(dto, AppUser.class);
        user.setKeycloakId(keycloakId);
        return user;
    }

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

        Response response = keycloak.realm("try2").users().create(user);

// ✅ Handle failure cleanly
        if (response.getStatus() != 201 || response.getLocation() == null) {
            throw new RuntimeException("Failed to create user in Keycloak. Status: " + response.getStatus());
        }

// ✅ Safe to extract user ID
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");


//        // 2. Save user in PostgreSQL
//        AppUser dbUser = new AppUser();
//        dbUser.setUsername(username);
//        dbUser.setEmail(email);
//        dbUser.setKeycloakId(userId);
//        userRepository.save(dbUser);

        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);
        AppUser dbUser = convertToAppUser(dto, userId);
        userRepository.save(dbUser);

    }

    public void resetPassword(String userId, String newPassword) {
        // 1. Find user in Keycloak
        var userResource = keycloak.realm("try2").users().get(userId);

        // 2. Build new password credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false); // Set to true if you want the user to be forced to change it

        // 3. Reset password in Keycloak
        userResource.resetPassword(credential);

    }

}
