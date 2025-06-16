package com.example.UserTaskManagerKeycloak.Controller;

import ch.qos.logback.classic.Logger;
import com.example.UserTaskManagerKeycloak.Dto.UserRegistrationDto;
import com.example.UserTaskManagerKeycloak.Service.JwtUtils;
import com.example.UserTaskManagerKeycloak.Service.PasswordResetService;
import com.example.UserTaskManagerKeycloak.Service.TokenService;
import com.example.UserTaskManagerKeycloak.Service.UserService;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    private final PasswordResetService resetService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService, PasswordResetService resetService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.resetService = resetService;
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello User!";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto dto) {
        userService.registerUser(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String newPassword,
                                                @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject(); // same as 'sub' claim (Keycloak User ID)
        userService.resetPassword(userId, newPassword);
        return ResponseEntity.ok("Password reset successfully!");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        resetService.sendResetEmail(email);
        return ResponseEntity.ok("Reset link sent!");
    }

    @PostMapping("/reset-password-mail")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token) {
        // Validate token & update password();
        System.out.println("Link redirected successfully!");
        return ResponseEntity.ok("Password updated!");
    }




}
