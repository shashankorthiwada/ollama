package com.learnai.ollama;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class DocumentService {

    public String readDocument(String filename) throws IOException {

        ClassPathResource resource =
                new ClassPathResource("documents/" + filename);

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}