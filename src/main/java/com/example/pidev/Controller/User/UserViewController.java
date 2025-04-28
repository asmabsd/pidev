package com.example.pidev.Controller.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.service.User.EmailService;
import com.example.pidev.service.User.PasswordService;
import com.example.pidev.service.User.ResetPasswordTokenService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
@Controller
@RequestMapping("/users/views")

public class UserViewController {
    @Autowired

    private final ResetPasswordTokenService tokenService;
    @Autowired

    private final EmailService emailService;
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;


    public UserViewController(ResetPasswordTokenService tokenService, EmailService emailService, PasswordService passwordService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) throws MessagingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String token = tokenService.createResetToken(user.get());
            emailService.sendResetPasswordEmail(email, token);
            model.addAttribute("email", email); // Passe l'email au template HTML
            return "forgot-password-success"; // Nom du fichier HTML sans extension
        } else {
            model.addAttribute("message", "Email non trouvé.");
            return "reset-password-error"; // Page d'erreur
        }
    }
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token); // Passe le token au template HTML
        return "reset-password"; // Nom du fichier HTML sans extension
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return tokenService.getToken(token)
                .map(resetToken -> {
                    if (resetToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
                        return "redirect:/users/reset-password-error?message=Token expiré.";
                    }
                    User user = resetToken.getUser();
                    // Crypter le nouveau mot de passe avant de l'enregistrer
                    String encryptedPassword = passwordService.encryptPassword(newPassword);
                    user.setPassword(encryptedPassword);
                    userRepository.save(user);
                    return "redirect:/users/views/reset-password-success"; // Redirigez vers une page de succès
                })
                .orElse("redirect:/users/views/reset-password-error?message=Token invalide.");
    }

    @GetMapping("/forgot-password-success")
    public String showForgotPasswordSuccessPage() {
        return "forgot-password-success"; // Page de succès pour l'envoi du lien
    }

    @GetMapping("/reset-password-success")
    public String showResetPasswordSuccessPage() {
        return "reset-password-success"; // Page de succès pour la réinitialisation
    }

    @GetMapping("/reset-password-error")
    public String showResetPasswordErrorPage(@RequestParam String message, Model model) {
        model.addAttribute("message", message); // Passe le message d'erreur au template
        return "reset-password-error"; // Page d'erreur
    }

}
