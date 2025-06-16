package com.example.UserTaskManagerKeycloak.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String email) {
        String token = UUID.randomUUID().toString();
        // Save token in DB (with expiry)
        String resetLink = "http://localhost:8081/reset-password-mail?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Click here to reset: " + resetLink);
        mailSender.send(message);
        System.out.println("email sent successfully");
    }
}

