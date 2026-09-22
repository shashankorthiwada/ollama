package com.learnai.ollama;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagIndexService {

    private final EmbeddingClient embeddingClient;
    private final VectorStore vectorStore;

    public RagIndexService(
            EmbeddingClient embeddingClient,
            VectorStore vectorStore) {

        this.embeddingClient = embeddingClient;
        this.vectorStore = vectorStore;
    }

    public void indexDocument(
            String id,
            String source,
            String text) {

        List<Double> embedding =
                embeddingClient.embed(text);

        DocumentChunk chunk = new DocumentChunk(
                id,
                source,
                text,
                embedding
        );

        vectorStore.add(chunk);
    }
}