package com.example.UserTaskManagerKeycloak.Controller;

import com.example.UserTaskManagerKeycloak.Dto.UserRegistrationDto;
import com.example.UserTaskManagerKeycloak.Service.TokenService;
import com.example.UserTaskManagerKeycloak.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello Admin!";
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

    @PostMapping("/token")
    public String token(@RequestParam String username, @RequestParam String password) {
        return tokenService.getAccessToken(username, password);
    }


}
