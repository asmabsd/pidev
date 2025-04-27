package com.example.pidev.service.User;

import com.example.pidev.dtos.LoginReponse;
import com.example.pidev.dtos.LoginUserDto;
import com.example.pidev.dtos.RegisterUserDto;
import com.example.pidev.entity.User.AccountStatus;
import com.example.pidev.entity.User.Role;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleService roleService,
            TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    // SIGN UP LOGIC
    public User signup(RegisterUserDto input) {
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        Role role = roleService.getRoleByName(input.getRole().toUpperCase());

        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(role);
        user.setnTel(input.getnTel());
        user.setNumPasseport(input.getNumPasseport());
        user.setUsing2FA(input.isUsing2FA());

        if ("GUIDE".equalsIgnoreCase(role.getName()) || "PARTNER".equalsIgnoreCase(role.getName())) {
            user.setStatus(AccountStatus.PENDING);
        } else {
            user.setStatus(AccountStatus.ACCEPTED);
        }

        if (input.isUsing2FA()) {
            String secret = twoFactorAuthenticationService.generateNewSecret();
            user.setSecret2FA(secret); // ✅ This is good
        }
        log.debug("Secret generated during sign-up: {}", user.getSecret2FA());
        log.debug("Current time: {}", System.currentTimeMillis());


        return userRepository.save(user);


    }

    // LOGIN LOGIC
    public User authenticate(LoginUserDto input) {
        // Authenticate using email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        // Fetch the authenticated user
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // ✅ 2FA check is removed from here
        log.debug("User {} authenticated successfully. 2FA enabled: {}", user.getEmail(), user.isUsing2FA());

        return user;
    }


    // 2FA OTP VERIFICATION METHOD (optional, used in /2fa/verify endpoint)
    public boolean verify2FACode(String email, String otpCode) {
        User user = getUserByEmail(email); // Method to fetch the user by email
        return user.isUsing2FA() &&
                otpCode != null &&
                twoFactorAuthenticationService.isOtpValid(user.getSecret2FA(), otpCode);
    }


    // UTILITY
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }
}
