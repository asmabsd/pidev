package com.example.pidev.Controller.User;

import com.example.pidev.dtos.LoginReponse;
import com.example.pidev.dtos.LoginUserDto;
import com.example.pidev.dtos.RegisterUserDto;
import com.example.pidev.dtos.UserDto;
import com.example.pidev.entity.User.User;
import com.example.pidev.service.User.AuthenticationService;
import com.example.pidev.service.User.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pidev.entity.User.AccountStatus;

@RequestMapping("/auth")
@RestController
@CrossOrigin ("http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginReponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Check if the user's account status is pending
        if (authenticatedUser.getStatus() == AccountStatus.PENDING) {
            return ResponseEntity.status(403)
                    .body(new LoginReponse().setMessage("Account is pending approval"));
        }

        // Generate JWT token if the account is not pending
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Prepare and return the login response
        LoginReponse loginResponse = new LoginReponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime())
                .setUser(new UserDto(authenticatedUser));

        return ResponseEntity.ok(loginResponse);
    }
    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserId(@RequestParam String email) {
        Long id = authenticationService.getUserIdByEmail(email);
        return ResponseEntity.ok(id);
    }

}