package com.example.pidev.service.User;

import com.example.pidev.entity.User.Role;
import com.example.pidev.repository.User.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> roleNames = Arrays.asList("ADMIN", "USER", "GUIDE", "PARTNER");

        for (String roleName : roleNames) {
            if (!roleRepository.findByName(roleName).isPresent()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Role ajouté : " + roleName);
            }
        }
    }
}
