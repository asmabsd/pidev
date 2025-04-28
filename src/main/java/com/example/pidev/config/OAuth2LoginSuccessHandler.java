package com.example.pidev.config;

import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.service.User.CustomOAuth2User; // Import your wrapper class
import com.example.pidev.service.User.JwtService;
import com.example.pidev.service.User.PasswordGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository; // Injection de UserRepository


    public OAuth2LoginSuccessHandler(JwtService jwtService, JavaMailSender mailSender, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        User user = oauthUser.getUser();

        // Générer un mot de passe temporaire
        String rawPassword = PasswordGenerator.generateRandomPassword(12);
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encryptedPassword);

        // Sauvegarder l'utilisateur dans la base de données
        userRepository.save(user);

        // Envoyer le mot de passe à l'email de l'utilisateur
        sendPasswordEmail(user.getEmail(), rawPassword);

        // Répondre avec un message de succès
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\":\"Compte créé avec succès. Un email avec un mot de passe temporaire a été envoyé. Veuillez vérifier votre email et vous connecter avec ce mot de passe. Une fois connecté, vous pourrez modifier votre mot de passe.\"}");

        // Rediriger vers la page de login (côté frontend Angular)
        response.sendRedirect("http://localhost:4200/login");

        clearAuthenticationAttributes(request);
    }


    private void sendPasswordEmail(String email, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Votre mot de passe temporaire pour vous connecter");
            helper.setText("Bonjour,\n\nVotre compte a été créé avec succès !\n\nVoici votre mot de passe temporaire : "
                    + password + "\n\nVeuillez le changer après votre connexion.\n\nCordialement,\nL'équipe");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}
