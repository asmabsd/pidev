package com.example.pidev.Controller.User;

import com.example.pidev.service.User.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin ("http://localhost:4200")
public class ChatController {

    @Autowired
    private GeminiService geminiService;
    @PostMapping("/chat")
    public Map<String, String> getChatResponse(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        System.out.println("Message reçu du front-end : " + userMessage); // Debug log

        try {
            String botResponse = geminiService.getGeminiResponse(userMessage);
            return Map.of("user", userMessage, "bot", botResponse);
        } catch (Exception e) {
            System.err.println("Erreur dans le controller : " + e.getMessage());
            return Map.of("user", userMessage, "bot", "Erreur serveur !");
        }
    }

}
