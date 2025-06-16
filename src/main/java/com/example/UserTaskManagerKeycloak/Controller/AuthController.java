package com.example.UserTaskManagerKeycloak.Controller;

import com.example.UserTaskManagerKeycloak.Service.TokenService;
import com.example.UserTaskManagerKeycloak.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    private final TokenService tokenService;

    @Autowired
    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(@RequestParam String username, @RequestParam String password) {
        return tokenService.getAccessToken(username, password);
    }

    @GetMapping("/secure")
    public ResponseEntity<String> getSecure(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok("Hello " + jwt.getClaimAsString("preferred_username"));
    }



}

