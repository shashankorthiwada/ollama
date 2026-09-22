package com.learnai.ollama;

import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagIndexService {

    private final EmbeddingClient embeddingClient;
    private final VectorStore vectorStore;
    private final DocumentChunker documentChunker;

    public RagIndexService(EmbeddingClient embeddingClient, VectorStore vectorStore, DocumentChunker documentChunker) {

        this.embeddingClient = embeddingClient;
        this.vectorStore = vectorStore;
        this.documentChunker = documentChunker;
    }

    public void indexDocument(String documentId, String source, String text) {

        List<String> chunks = documentChunker.chunk(text);

        for (int i = 0; i < chunks.size(); i++) {

            String chunkText = chunks.get(i);

            List<Double> embedding = embeddingClient.embed(chunkText);

            DocumentChunk documentChunk = new DocumentChunk(documentId + "-chunk-" + i, source, chunkText, embedding);

            vectorStore.add(documentChunk);
        }
    }
}