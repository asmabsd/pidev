package com.example.pidev.service.User;


import java.security.SecureRandom;
import java.util.Base64;

public class PasswordGenerator {
    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
    }
}
