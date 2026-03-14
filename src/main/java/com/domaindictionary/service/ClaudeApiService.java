package com.domaindictionary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class ClaudeApiService {
    private static final Logger log = LoggerFactory.getLogger(ClaudeApiService.class);
    private static final String API_URL = "https://api.anthropic.com/v1/messages";

    @Value("${app.claude.api-key:}")
    private String apiKey;

    @Value("${app.claude.model:claude-sonnet-4-6}")
    private String model;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getDefinition(String term) {
        String prompt = "Give a concise definition of the term \"" + term +
                "\" in the language of the term. Respond with only the definition, no extra text.";
        return callClaude(prompt);
    }

    public List<String> filterDefinitionsByDomain(String term, List<String> definitions, String domainContext) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Given the term \"").append(term).append("\" with these definitions:\n");
        for (int i = 0; i < definitions.size(); i++) {
            prompt.append(i + 1).append(". ").append(definitions.get(i)).append("\n");
        }
        prompt.append("\nDomain context: ").append(domainContext);
        prompt.append("\n\nSelect the definition number(s) most relevant to this domain. ");
        prompt.append("Respond with only the numbers separated by commas.");

        String response = callClaude(prompt.toString());
        if (response == null) return definitions;

        try {
            List<Integer> indices = parseNumberList(response);
            if (indices.isEmpty()) return definitions;
            return indices.stream()
                    .filter(i -> i > 0 && i <= definitions.size())
                    .map(i -> definitions.get(i - 1))
                    .toList();
        } catch (Exception e) {
            return definitions;
        }
    }

    public List<String> findSynonyms(String term) {
        String prompt = "List synonyms for the term \"" + term +
                "\" in the language of the term. Respond with a comma-separated list, nothing else.";
        String response = callClaude(prompt);
        if (response == null || response.isBlank()) return List.of();
        return List.of(response.split(",")).stream().map(String::trim).filter(s -> !s.isEmpty()).toList();
    }

    private String callClaude(String userMessage) {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("Claude API key not configured");
            return null;
        }
        try {
            Map<String, Object> body = Map.of(
                    "model", model,
                    "max_tokens", 1024,
                    "messages", List.of(Map.of("role", "user", "content", userMessage))
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", apiKey)
                    .header("anthropic-version", "2023-06-01")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Claude API error: {} {}", response.statusCode(), response.body());
                return null;
            }

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode content = root.path("content");
            if (content.isArray() && !content.isEmpty()) {
                return content.get(0).path("text").asText();
            }
        } catch (Exception e) {
            log.error("Claude API call failed: {}", e.getMessage());
        }
        return null;
    }

    private List<Integer> parseNumberList(String text) {
        return List.of(text.replaceAll("[^0-9,]", "").split(",")).stream()
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }
}
