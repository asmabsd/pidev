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
            String resetLink = "http://localhost:4200/reset-password?token=" + token;
            String subject = "Réinitialisation de votre mot de passe";
            String content = "<p>Bonjour,</p>"
                    + "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>"
                    + "<p>Cliquez sur le lien ci-dessous pour le réinitialiser :</p>"
                    + "<p><a href=\"" + resetLink + "\">Réinitialiser le mot de passe</a></p>"
                    + "<p>Ce lien est valable pour 1 heure.</p>";

            return sendEmail(email, subject, content);
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de l'email à {}: {}", email, e.getMessage());
            return false;
        }
    }
    public boolean sendApprovalEmail(String email, String name, String userType) {
        try {
            String subject = "Your " + userType + " account has been approved";
            String content = "<p>Hello " + name + ",</p>"
                    + "<p>We are pleased to inform you that your " + userType + " account has been approved.</p>"
                    + "<p>You can now log in and start using our platform.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>The Administration Team</p>";

            return sendEmail(email, subject, content);
        } catch (Exception e) {
            logger.error("Error sending approval email to {}: {}", email, e.getMessage());
            return false;
        }
    }

    public boolean sendRejectionEmail(String email, String name, String userType) {
        try {
            String subject = "Your " + userType + " account request";
            String content = "<p>Hello " + name + ",</p>"
                    + "<p>We regret to inform you that your request for a " + userType + " account has not been approved.</p>"
                    + "<p>If you have any questions, feel free to contact our support team.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>The Administration Team</p>";

            return sendEmail(email, subject, content);
        } catch (Exception e) {
            logger.error("Error sending rejection email to {}: {}", email, e.getMessage());
            return false;
        }
    }


    private boolean sendEmail(String email, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        logger.info("Email envoyé à : {}", email);
        return true;
    }
}