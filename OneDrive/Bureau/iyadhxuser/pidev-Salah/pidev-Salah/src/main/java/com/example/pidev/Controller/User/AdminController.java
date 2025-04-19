package com.example.pidev.Controller.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.service.User.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin ("http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Endpoint to get users with 'PENDING' status
    @GetMapping("/pending-users")
    public ResponseEntity<List<User>> getPendingUsers() {
        List<User> pendingUsers = adminService.getPendingUsers();
        if (pendingUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pendingUsers, HttpStatus.OK);
    }

    // Endpoint to approve a user
    @PutMapping("/approve-user/{userId}")
    public ResponseEntity<String> approveUser(@PathVariable Long userId) {
        try {
            adminService.approveUser(userId);
            return new ResponseEntity<>("User successfully approved", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to reject a user
    @PutMapping("/reject-user/{userId}")
    public ResponseEntity<String> rejectUser(@PathVariable Long userId) {
        try {
            adminService.rejectUser(userId);
            return new ResponseEntity<>("User successfully rejected", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
