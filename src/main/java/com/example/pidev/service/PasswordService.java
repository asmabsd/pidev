package com.example.pidev.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    // Constructor for dependency injection
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Crypter un mot de passe
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Vérifier si le mot de passe correspond au mot de passe crypté
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
