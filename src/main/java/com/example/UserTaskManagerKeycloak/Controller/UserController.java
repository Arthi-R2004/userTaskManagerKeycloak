package com.example.UserTaskManagerKeycloak.Controller;

import com.example.UserTaskManagerKeycloak.Dto.UserRegistrationDto;
import com.example.UserTaskManagerKeycloak.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

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

}
