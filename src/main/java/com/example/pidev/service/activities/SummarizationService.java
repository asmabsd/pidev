package com.example.pidev.service.activities;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SummarizationService {
    private final String API_KEY = "aEZPpPPvcxzJF4YIK4XmuHiAw8OX1PFqvMVE3CdO"; // Remplacez par votre clé API Cohere
    private final String ENDPOINT = "https://api.cohere.ai/v1/summarize";

    public String summarizeText(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("text", text);
        body.put("length", "medium"); // Options: short, medium, long
        body.put("format", "paragraph"); // Options: paragraph, bullets
        body.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = response.getBody();
            return responseBody.get("summary").trim();
        }

        return "Résumé non généré.";
    }
}