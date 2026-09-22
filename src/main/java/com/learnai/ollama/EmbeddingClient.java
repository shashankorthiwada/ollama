package com.learnai.ollama;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingClient {

    private final RestClient client = RestClient.builder()
            .baseUrl("http://localhost:11434")
            .build();

    public List<Double> embed(String text) {

        Map<String, Object> request = Map.of(
                "model", "nomic-embed-text",
                "input", text
        );

        Map<?, ?> response = client.post()
                .uri("/api/embed")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(Map.class);

        @SuppressWarnings("unchecked")
        List<List<Double>> embeddings =
                (List<List<Double>>) response.get("embeddings");

        return embeddings.get(0);
    }
}