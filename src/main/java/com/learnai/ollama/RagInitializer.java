package com.learnai.ollama;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RagInitializer implements CommandLineRunner {

    private final RagIndexService ragIndexService;
    private final DocumentService documentService;

    public RagInitializer(RagIndexService ragIndexService, DocumentService documentService) {

        this.ragIndexService = ragIndexService;
        this.documentService = documentService;
    }

    @Override
    public void run(String... args) throws Exception {

        index("refund-1", "refund-policy.txt");

        index("credit-card-1", "credit-card-policy.txt");

        index("account-1", "account-policy.txt");

        System.out.println("RAG index created.");
    }

    private void index(String id, String filename) throws Exception {

        String text = documentService.readDocument(filename);

        ragIndexService.indexDocument(id, filename, text);
    }
}
