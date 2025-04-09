package com.example.pidev.service.User;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendResetPasswordEmail(String email, String token) {
        try {
            // ✅ Changer l'URL pour pointer vers Angular (frontend)
            String resetLink = "http://localhost:4200/reset-password?token=" + token;
            String subject = "Réinitialisation de votre mot de passe";
            String content = "<p>Bonjour,</p>"
                    + "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>"
                    + "<p>Cliquez sur le lien ci-dessous pour le réinitialiser :</p>"
                    + "<p><a href=\"" + resetLink + "\">Réinitialiser le mot de passe</a></p>"
                    + "<p>Ce lien est valable pour 1 heure.</p>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);

            logger.info("Email de réinitialisation envoyé à : {}", email);
            return true;

        } catch (MessagingException e) {
            logger.error("Erreur lors de l'envoi de l'email à {}: {}", email, e.getMessage());
            return false;
        }
    }
}
