package com.example.pidev.service.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Crypte un mot de passe en utilisant BCrypt.
     * @param password Le mot de passe en clair.
     * @return Le mot de passe crypté.
     */
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Vérifie si un mot de passe correspond à son hash.
     * @param rawPassword Le mot de passe en clair.
     * @param encodedPassword Le mot de passe déjà crypté.
     * @return true si les mots de passe correspondent, sinon false.
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
