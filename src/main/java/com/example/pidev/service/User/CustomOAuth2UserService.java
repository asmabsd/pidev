package com.example.pidev.service.User;

import com.example.pidev.entity.User.AccountStatus;
import com.example.pidev.entity.User.User;
import com.example.pidev.entity.User.Role;
import com.example.pidev.repository.User.RoleRepository;
import com.example.pidev.repository.User.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String DEFAULT_ROLE = "USER";



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender; // Pour envoyer l'email

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Pour crypter le mot de passe

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAuthProvider(User.AuthProvider.GOOGLE);
            user.setStatus(AccountStatus.ACCEPTED);


            // ✅ Génération et cryptage du mot de passe
            String rawPassword = PasswordGenerator.generateRandomPassword(12);
            String encryptedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encryptedPassword);


            Role userRole = getOrCreateRole(DEFAULT_ROLE);
            user.setRole(userRole);

            user = userRepository.save(user);



        }

        return new CustomOAuth2User(user, attributes);
    }


    private Role getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
    }


}
