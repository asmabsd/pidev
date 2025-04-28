package com.example.pidev.service.User;

import com.example.pidev.entity.User.ResetPasswordToken;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.ResetPasswordTokenRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository tokenRepository;

    public ResetPasswordTokenService(ResetPasswordTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String createResetToken(User user) {
        // Supprimer les anciens tokens
        tokenRepository.deleteByUserId(user.getId());

        // Générer un nouveau token
        String token = UUID.randomUUID().toString();
        ResetPasswordToken resetToken = new ResetPasswordToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Expire après 1h

        tokenRepository.save(resetToken);
        return token;
    }

    public Optional<ResetPasswordToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
