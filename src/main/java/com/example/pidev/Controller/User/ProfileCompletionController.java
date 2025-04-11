package com.example.pidev.Controller.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.service.User.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Correct import
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/complete-profile")
public class ProfileCompletionController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ProfileCompletionController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showCompletionForm(@RequestParam String token, Model model) {
        try {
            // First extract the username from the token
            String email = jwtService.extractUsername(token);

            // Then get the user details
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Now validate the token with the user details
            if (!jwtService.isTokenValid(token, user)) {
                return "redirect:/login?error=invalid_token";
            }

            model.addAttribute("token", token);
            return "complete-profile";
        } catch (Exception e) {
            return "redirect:/login?error=token_validation_failed";
        }
    }

    @PostMapping
    public ResponseEntity<?> completeProfile(
            @RequestParam String token,
            @RequestParam String nTel,
            @RequestParam String numPasseport) {

        try {
            String email = jwtService.extractUsername(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Validate token again for POST request
            if (!jwtService.isTokenValid(token, user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid token");
            }

            // Validate inputs
            if (!nTel.matches("^[0-9]{8}$")) {
                return ResponseEntity.badRequest().body("Phone number must be 8 digits");
            }

            if (!numPasseport.matches("^[A-Za-z0-9]{9}$")) {
                return ResponseEntity.badRequest().body("Passport must be 9 alphanumeric chars");
            }

            user.setnTel(nTel);
            user.setNumPasseport(numPasseport);
            userRepository.save(user);

            return ResponseEntity.ok().body(Map.of(
                    "message", "Profile completed successfully",
                    "redirect", "/dashboard"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}