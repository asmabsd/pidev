package com.example.pidev.service.User;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final String apiKey = "AIzaSyDQdsjlrFYKODaku3FSZ0fLC-pIckLY0lw";
    private final WebClient webClient;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String getGeminiResponse(String message) {
        // Construction du corps de la requête
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", message)
                        ))
                )
        );

        try {
            // Requête POST
            Map<String, Object> response = webClient.post()
                    .uri("/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // Traitement de la réponse
            if (response != null && response.containsKey("candidates")) {
                List<?> candidates = (List<?>) response.get("candidates");
                if (!candidates.isEmpty() && candidates.get(0) instanceof Map) {
                    Map<?, ?> candidate = (Map<?, ?>) candidates.get(0);
                    Map<?, ?> content = (Map<?, ?>) candidate.get("content");
                    if (content != null) {
                        List<?> parts = (List<?>) content.get("parts");
                        if (!parts.isEmpty() && parts.get(0) instanceof Map) {
                            Map<?, ?> part = (Map<?, ?>) parts.get(0);
                            Object text = part.get("text");
                            if (text != null) {
                                return text.toString();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel à l'API Gemini : " + e.getMessage());
        }

        return "Je ne comprends pas.";
    }
}
