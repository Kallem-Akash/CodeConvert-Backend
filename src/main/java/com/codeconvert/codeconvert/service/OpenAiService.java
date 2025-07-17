package com.codeconvert.codeconvert.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.api.url}")
    private  String API_URL;

    public String convertCode(String sourceCode, String fromLang, String toLang) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = String.format(
                "Convert the following code from %s to %s. Return only valid, formatted code. No explanation, markdown, or comments.\n\n%s",
                fromLang, toLang, sourceCode
        );

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "temperature", 0.3
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message.get("content").toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "// Error: OpenAI API failed."+e.getMessage();
        }
    }
}
