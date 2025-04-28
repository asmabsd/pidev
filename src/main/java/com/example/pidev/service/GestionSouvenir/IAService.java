package com.example.pidev.service.GestionSouvenir;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IAService {
    private final String API_KEY = "Ob9PCXvDGqeVd9BeQky1DkNsi2qeFpUD88irlNcV";
    private final String ENDPOINT = "https://api.cohere.ai/v1/generate";

    public String generateDescription(String name, String category, double price) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prompt en anglais avec instruction de limiter à 150 caractères
        String prompt = String.format(
                "Write a short, attractive description in English of a souvenir called '%s' in the category '%s' that costs %.2f dinars. The description should be no longer than 150 characters.",
                name, category, price
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", "command");
        body.put("prompt", prompt);
        body.put("max_tokens", 100);
        body.put("temperature", 0.9);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, String>> generations = (List<Map<String, String>>) response.getBody().get("generations");
            String description = generations.get(0).get("text").trim();

            // Vérifier et tronquer la description si elle dépasse 150 caractères
            if (description.length() > 150) {
                int lastSpace = description.lastIndexOf(' ', 150);
                if (lastSpace > 0) {
                    description = description.substring(0, lastSpace); // Tronquer au dernier espace
                } else {
                    description = description.substring(0, 150); // Tronquer à 150 si aucun espace
                }
            }

            return description;
        }

        return "Description not generated.";
    }
}