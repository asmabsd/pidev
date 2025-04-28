package com.example.pidev.service.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret}")
    private String secretKey; // Secret Key from application.properties

    private final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyCaptcha(String recaptchaResponse) {
        // Build the URL with query parameters
        String url = UriComponentsBuilder.fromHttpUrl(VERIFY_URL)
                .queryParam("secret", secretKey)
                .queryParam("response", recaptchaResponse)
                .toUriString();

        // Set up headers and make the request
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Send the request to Google's reCAPTCHA API
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Check the response from Google
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                return responseBody != null && responseBody.contains("\"success\": true");
            } else {
                // Log the error or handle it appropriately
                return false;
            }
        } catch (Exception e) {
            // Handle the exception (e.g., log it)
            return false;
        }
    }
}
