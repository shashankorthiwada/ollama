package com.learnai.ollama;

import java.util.List;

public record DocumentChunk(String id, String source, String text, List<Double> embedding) {
}
