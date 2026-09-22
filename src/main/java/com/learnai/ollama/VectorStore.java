package com.learnai.ollama;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VectorStore {

    private final List<DocumentChunk> documents = new ArrayList<>();

    public void add(DocumentChunk document) {
        documents.add(document);
    }

    public List<DocumentChunk> getDocuments() {
        return documents;
    }

    public void clear() {
        documents.clear();
    }
}
