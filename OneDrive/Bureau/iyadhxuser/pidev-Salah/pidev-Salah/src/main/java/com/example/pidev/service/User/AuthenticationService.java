package com.example.pidev.service.User;

import com.example.pidev.dtos.LoginUserDto;
import com.example.pidev.dtos.RegisterUserDto;
import com.example.pidev.entity.User.Role;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pidev.entity.User.AccountStatus;


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
        // Check if the email already exists
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        // Fetch the role from the database using the role name
        Role role = roleService.getRoleByName(input.getRole().toUpperCase());

        // Create a new user entity
        User user = new User();
        user.setId(user.getId()); // <- ce champ manquait
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(role); // Set the role fetched from the database
        user.setnTel(input.getnTel());
        user.setNumPasseport(input.getNumPasseport());

        if ("GUIDE".equalsIgnoreCase(role.getName())) {
            user.setStatus(AccountStatus.PENDING);
        } else if ("PARTNER".equalsIgnoreCase(role.getName())) {
            user.setStatus(AccountStatus.PENDING);
        } else {
            user.setStatus(AccountStatus.ACCEPTED);
        }



        // Save the user entity to the repository
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
    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
