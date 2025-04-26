package com.example.pidev.service.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.User.AccountStatus;
import com.example.pidev.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public AdminService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<User> getPendingUsers() {
        return userRepository.findByStatus(AccountStatus.PENDING);
    }

    @Transactional
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(AccountStatus.ACCEPTED);
        userRepository.save(user);

        // Determine user type for email (Guide or Partner)
        String userType = determineUserType(user);

        // Send approval email
        emailService.sendApprovalEmail(user.getEmail(), user.getFirstName(), userType);
    }

    @Transactional
    public void rejectUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(AccountStatus.REJECTED);
        userRepository.save(user);

        // Determine user type for email (Guide or Partner)
        String userType = determineUserType(user);

        // Send rejection email
        emailService.sendRejectionEmail(user.getEmail(), user.getFirstName(), userType);
    }

    private String determineUserType(User user) {
        // Adjust this logic based on how you identify guides vs partners in your system
        // For example, you might check the user's role
        if (user.getRole() != null && user.getRole().getName().equalsIgnoreCase("GUIDE")) {
            return "Guide";
        } else if (user.getRole() != null && user.getRole().getName().equalsIgnoreCase("PARTNER")) {
            return "Partenaire";
        }
        return "Utilisateur"; // Default fallback
    }
}