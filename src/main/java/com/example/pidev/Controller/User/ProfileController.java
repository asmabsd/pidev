package com.example.pidev.Controller.User;

import com.example.pidev.dtos.ProfileDto;
import com.example.pidev.entity.User.User;
import com.example.pidev.service.User.UserService;
import com.example.pidev.service.User.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin ("http://localhost:4200")
@RequiredArgsConstructor
public class ProfileController {

     UserService userService;
     JwtService jwtService;

    @PutMapping("/complete")
    public ResponseEntity<?> completeProfile(@Valid @RequestBody ProfileDto profileDto, @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractUsername(token);

        User updatedUser = userService.updateUserProfile(userEmail, profileDto);

        return ResponseEntity.ok(updatedUser);
    }
}
