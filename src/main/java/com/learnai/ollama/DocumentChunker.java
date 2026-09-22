package com.learnai.ollama;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentChunker {

    public List<String> chunk(String text) {

        String normalizedText = text.replaceAll("\\s+", " ").trim();

        List<String> sentences = Arrays.stream(normalizedText.split("(?<=[.!?])\\s+")).map(String::trim).filter(sentence -> !sentence.isBlank()).toList();

        int chunkSize = 2;
        int overlap = 1;

        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < sentences.size(); i += chunkSize - overlap) {

            int end = Math.min(i + chunkSize, sentences.size());

            String chunk = String.join(" ", sentences.subList(i, end));

            chunks.add(chunk);

            if (end == sentences.size()) {
                break;
            }
        }

        return chunks;
    }
}
