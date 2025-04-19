package com.example.pidev.Controller.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Utilisez un chemin différent pour les API REST
@CrossOrigin ("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) { // Changement de int -> Long
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) { // Changement de int -> Long
        User user = userService.getUser(id);
        userService.deleteUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) { // Changement de int -> Long
        return userService.updateUser(id, user);
    }


    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user) {
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build(); // Non autorisé si l'utilisateur est nul
        }
    }



}

