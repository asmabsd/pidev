package com.example.pidev.Controller.activities;

import com.example.pidev.service.activities.SummarizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/resume")
@CrossOrigin(origins = "http://localhost:4200")
public class SummarizationController {

    private final SummarizationService summarizationService;

    public SummarizationController(SummarizationService summarizationService) {
        this.summarizationService = summarizationService;
        System.out.println("SummarizationController initialisé");
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeText(@RequestBody Map<String, Object> fields) {
        String text = (String) fields.get("text");
        if (text == null || text.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Texte vide ou non fourni");
        }
        String summarizedText = summarizationService.summarizeText(text);
        return ResponseEntity.ok(summarizedText);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("Test endpoint appelé");
        return ResponseEntity.ok("Test réussi");
    }
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Bonjour depuis le backend !");
    }

}