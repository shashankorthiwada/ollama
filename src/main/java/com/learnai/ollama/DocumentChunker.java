package com.learnai.ollama;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DocumentChunker {

    public List<String> chunk(String text) {

        return Arrays.stream(text.split("\\n\\s*\\n")).map(String::trim).filter(chunk -> !chunk.isBlank()).toList();
    }
}
