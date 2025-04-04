package com.example.pidev.Controller.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.User.ResetPasswordToken;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.service.User.EmailService;
import com.example.pidev.service.User.PasswordService;
import com.example.pidev.service.User.ResetPasswordTokenService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
public class UserViewController {

    private final ResetPasswordTokenService tokenService;
    private final EmailService emailService;
    private final PasswordService passwordService;
    private final UserRepository userRepository;

    @Autowired
    public UserViewController(ResetPasswordTokenService tokenService,
                              EmailService emailService,
                              PasswordService passwordService,
                              UserRepository userRepository) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "Aucun utilisateur trouvé avec cet email"));
            }

            User user = userOptional.get();
            String token = tokenService.createResetToken(user);
            boolean emailSent = emailService.sendResetPasswordEmail(email, token);

            if (!emailSent) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("success", false, "message", "Erreur lors de l'envoi de l'email"));
            }

            return ResponseEntity.ok()
                    .body(Map.of("success", true, "message", "Email de réinitialisation envoyé", "email", email));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erreur interne du serveur"));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        return tokenService.getToken(token)
                .map(resetToken -> {
                    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                        return ResponseEntity.ok()
                                .body(Map.of("valid", false, "message", "Token expiré"));
                    }
                    return ResponseEntity.ok()
                            .body(Map.of("valid", true, "message", "Token valide"));
                })
                .orElse(ResponseEntity.ok()
                        .body(Map.of("valid", false, "message", "Token invalide")));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            Optional<ResetPasswordToken> tokenOptional = tokenService.getToken(request.getToken());
            if (tokenOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Token invalide"));
            }

            ResetPasswordToken resetToken = tokenOptional.get();
            if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Token expiré"));
            }

            if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Le mot de passe ne peut pas être vide"));
            }

            User user = resetToken.getUser();
            String encryptedPassword = passwordService.encryptPassword(request.getNewPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);

            // Optionnel : Invalider le token après utilisation
            // tokenService.invalidateToken(request.getToken());

            return ResponseEntity.ok()
                    .body(Map.of("success", true, "message", "Mot de passe réinitialisé avec succès"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erreur lors de la réinitialisation du mot de passe"));
        }
    }

    public static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        // Getters et Setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}