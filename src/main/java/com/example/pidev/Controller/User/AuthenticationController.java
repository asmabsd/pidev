package com.example.pidev.Controller.User;

import com.example.pidev.dtos.*;
import com.example.pidev.entity.User.AccountStatus;
import com.example.pidev.entity.User.User;
import com.example.pidev.service.User.AuthenticationService;
import com.example.pidev.service.User.JwtService;
import com.example.pidev.service.User.TwoFactorAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService,
                                    TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> register(@RequestBody RegisterUserDto registerUserDto) {
        // Register the user
        User registeredUser = authenticationService.signup(registerUserDto);

        // Variable for QR Code URI
        String qrCodeUri = null;

        // Check if 2FA is enabled
        if (registeredUser.isUsing2FA()) {
            // Generate QR code URI including the user's email
            qrCodeUri = twoFactorAuthenticationService.generateQrCodeImageUri(registeredUser.getSecret2FA(), registeredUser.getEmail());
        }

        // Prepare the response with user data and the QR code URI (if 2FA is enabled)
        SignupResponseDto response = new SignupResponseDto(registeredUser, qrCodeUri);

        // Return the response
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        if (authenticatedUser.getStatus() == AccountStatus.PENDING) {
            return ResponseEntity.status(403)
                    .body(new LoginReponse().setMessage("Account is pending approval"));
        }

        if (authenticatedUser.isUsing2FA()) {
            return ResponseEntity.status(206) // Partial Content — expecting OTP
                    .body(new LoginReponse()
                            .setMessage("2FA enabled. Please verify OTP.")
                            .setUsing2FA(true) // ✅ Ajout essentiel
                            .setUser(new UserDto(authenticatedUser)));
        }


        String jwtToken = jwtService.generateToken(authenticatedUser);

        // ✅ Log the token to the console (for dev/debugging)
        System.out.println("✅ JWT Token for " + authenticatedUser.getEmail() + " : " + jwtToken);

        LoginReponse loginResponse = new LoginReponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime())
                .setUser(new UserDto(authenticatedUser));


        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationDto otpVerificationDto) {
        User user = authenticationService.getUserByEmail(otpVerificationDto.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginReponse().setMessage("User not found."));
        }

        if (user.isUsing2FA()) {
            boolean isOtpValid = authenticationService.verify2FACode(otpVerificationDto.getEmail(), otpVerificationDto.getOtpCode());
            if (isOtpValid) {
                String jwtToken = jwtService.generateToken(user);
                LoginReponse loginResponse = new LoginReponse()
                        .setToken(jwtToken)
                        .setExpiresIn(jwtService.getExpirationTime())
                        .setUser(new UserDto(user))
                        .setUsing2FA(user.isUsing2FA()); // Include 2FA status
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new LoginReponse().setMessage("Invalid OTP. Please try again."));
            }
        } else {
            // Handle case where user does not have 2FA enabled
            String jwtToken = jwtService.generateToken(user);
            LoginReponse loginResponse = new LoginReponse()
                    .setToken(jwtToken)
                    .setExpiresIn(jwtService.getExpirationTime())
                    .setUser(new UserDto(user))
                    .setUsing2FA(user.isUsing2FA());
            return ResponseEntity.ok(loginResponse);
        }
    }




    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserId(@RequestParam String email) {
        Long id = authenticationService.getUserIdByEmail(email);
        return ResponseEntity.ok(id);
    }
}
