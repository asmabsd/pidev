package com.example.pidev.service.User;

import com.example.pidev.dtos.LoginUserDto;
import com.example.pidev.dtos.RegisterUserDto;
import com.example.pidev.entity.User.Role;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService; // Ajout du RoleService

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleService roleService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User signup(RegisterUserDto input) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        // Récupérer le rôle depuis la base de données en utilisant une chaîne de caractères
        Role role = roleService.getRoleByName(input.getRole().toUpperCase());

        // Créer l'utilisateur
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(role); // Affecter le rôle récupéré
        user.setNTel(input.getNTel());
        user.setNumPasseport(input.getNumPasseport());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
