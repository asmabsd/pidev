package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.EmailRequest;

import com.example.pidev.service.GUIDE.mailservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:4200/")

public class EmailController {

    @Autowired
    private mailservice Mailservice;

    @PostMapping("/sendemail")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailRequest emailRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            Mailservice.sendEmail(emailRequest.getGuideEmail(), emailRequest.getUserEmail(),
                    "Contact for  guide booking  ", "check your account please <3");
            response.put("message", "Email sent successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error sending email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }
}
