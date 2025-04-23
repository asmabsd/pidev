package com.example.pidev.service.GestionSouvenir;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IAService {
    private final String ENDPOINT = "https://api.cohere.ai/v1/generate";

    public String generateDescription(String name, String category, double price) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = String.format("Rédige une description attrayante d’un souvenir appelé '%s' dans la catégorie '%s' qui coûte %.2f dinars.",
                name, category, price);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "command");
        body.put("prompt", prompt);
        body.put("max_tokens", 100);
        body.put("temperature", 0.9);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, String>> generations = (List<Map<String, String>>) response.getBody().get("generations");
            return generations.get(0).get("text").trim();
        }

        return "Description non générée.";
    }
}
