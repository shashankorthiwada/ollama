package com.learnai.ollama;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class DocumentService {

    public String getRefundPolicy() throws IOException {
        ClassPathResource resource =
                new ClassPathResource("documents/refund-policy.txt");

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }

    public String getCreditCardPolicy() throws IOException {
        ClassPathResource resource =
                new ClassPathResource("documents/credit-card-policy.txt");

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }

    public String getAccountPolicy() throws IOException {
        ClassPathResource resource =
                new ClassPathResource("documents/account-policy.txt");

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}