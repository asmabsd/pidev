package com.example.pidev.service.User;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
@Slf4j
public class TwoFactorAuthenticationService {

    private static final String ISSUER = "Salah";
    private static final String LABEL_PREFIX = "Salah:"; // Consistent label prefix

    private final SecretGenerator secretGenerator = new DefaultSecretGenerator();
    private final TimeProvider timeProvider = new SystemTimeProvider();
    private final CodeGenerator codeGenerator = new DefaultCodeGenerator();
    private final CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    private final QrGenerator qrGenerator = new ZxingPngQrGenerator();

    /**
     * Generates a new 2FA secret key.
     */
    public String generateNewSecret() {
        String secret = secretGenerator.generate();
        // Ensure proper Base32 formatting
        secret = cleanSecretKey(secret);
        return secret;
    }

    /**
     * Generates a QR code URI to be rendered as an image (data URI) for the user.
     * @param secret The secret key for 2FA
     * @param email The user's email to personalize the QR code label
     * @return Data URI of the QR code image
     */
    public String generateQrCodeImageUri(String secret, String email) {
        secret = cleanSecretKey(secret); // Ensure secret is properly formatted

        QrData data = new QrData.Builder()
                .label(LABEL_PREFIX + email)
                .secret(secret)
                .issuer(ISSUER)
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(100)
                .build();

        try {
            byte[] imageData = qrGenerator.generate(data);
            return getDataUriForImage(imageData, qrGenerator.getImageMimeType());
        } catch (QrGenerationException e) {
            throw new RuntimeException("QR Code generation failed", e);
        }
    }

    /**
     * Verifies if the provided OTP code is valid for the given secret.
     * @param secret The user's secret key
     * @param code The OTP code to verify
     * @return true if valid, false otherwise
     */
    public boolean isOtpValid(String secret, String code) {
        if (!isValidSecretFormat(secret)) {
            return false;
        }

        return codeVerifier.isValidCode(secret, code);
    }

    /**
     * Cleans and formats a secret key to proper Base32 format.
     * @param secret The raw secret key
     * @return Cleaned and properly formatted secret key
     */
    public String cleanSecretKey(String secret) {
        if (secret == null) return null;

        // Remove all whitespace and convert to uppercase
        secret = secret.replaceAll("\\s+", "").toUpperCase();

        // Ensure proper padding
        int mod = secret.length() % 8;
        if (mod != 0) {
            secret = secret + "========".substring(mod);
        }

        return secret;
    }

    /**
     * Validates the format of a secret key.
     * @param secret The secret key to validate
     * @return true if valid Base32 format, false otherwise
     */
    public boolean isValidSecretFormat(String secret) {
        if (secret == null || secret.isEmpty()) {
            return false;
        }

        // Base32 regex (letters A-Z, 2-7, optional padding with =)
        return secret.matches("^[A-Z2-7]+=*$");
    }

    /**
     * Convenience method to check if OTP is not valid.
     * @param secret The user's secret key
     * @param code The OTP code to verify
     * @return true if invalid, false if valid
     */
    public boolean isOtpNotValid(String secret, String code) {
        return !isOtpValid(secret, code);
    }
}