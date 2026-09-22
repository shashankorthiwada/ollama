package com.learnai.ollama;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RagRetrievalService {

    private final EmbeddingClient embeddingClient;
    private final VectorStore vectorStore;
    private final VectorSimilarity vectorSimilarity;

    public RagRetrievalService(EmbeddingClient embeddingClient, VectorStore vectorStore, VectorSimilarity vectorSimilarity) {

        this.embeddingClient = embeddingClient;
        this.vectorStore = vectorStore;
        this.vectorSimilarity = vectorSimilarity;
    }

    public List<DocumentChunk> retrieve(String question, int topK) {

        List<Double> questionEmbedding = embeddingClient.embed(question);

        return vectorStore.getDocuments().stream().map(document -> {

            double similarity = vectorSimilarity.cosineSimilarity(questionEmbedding, document.embedding());

            return new ScoredDocument(document, similarity);
        }).sorted(Comparator.comparingDouble(ScoredDocument::score).reversed()).limit(topK).map(ScoredDocument::document).toList();
    }

    private record ScoredDocument(DocumentChunk document, double score) {
    }
}
