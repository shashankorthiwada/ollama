package com.learnai.ollama;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class OllamaClient {

    private final RestClient client = RestClient.builder().baseUrl("http://localhost:11434").build();

    public String ask(String prompt) {

        Map<String, Object> request = Map.of("model", "llama3.2:3b", "prompt", prompt, "stream", false);

        Map<?, ?> response = client.post().uri("/api/generate").contentType(MediaType.APPLICATION_JSON).body(request).retrieve().body(Map.class);

        System.out.println("========== OLLAMA RESPONSE ==========");
        System.out.println(response);
        System.out.println("======================================");

        return response.get("response").toString();
    }

    public OllamaResponse chat(List<Map<String, Object>> messages, List<Map<String, Object>> tools) {

        Map<String, Object> request = Map.of("model", "llama3.2:3b", "messages", messages, "tools", tools, "stream", false);

        return client.post().uri("/api/chat").contentType(MediaType.APPLICATION_JSON).body(request).retrieve().body(OllamaResponse.class);
    }
}