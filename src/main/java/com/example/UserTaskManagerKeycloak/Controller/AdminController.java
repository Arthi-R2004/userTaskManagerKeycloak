package com.example.UserTaskManagerKeycloak.Controller;

import com.example.UserTaskManagerKeycloak.Service.TokenService;
import com.example.UserTaskManagerKeycloak.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;

    private final TokenService tokenService;

    @Autowired
    public AdminController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello Admin!";
    }


}

