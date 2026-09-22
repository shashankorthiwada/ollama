package com.learnai.ollama;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagAnswerService {

    private final RagRetrievalService retrievalService;
    private final OllamaClient ollamaClient;

    public RagAnswerService(RagRetrievalService retrievalService, OllamaClient ollamaClient) {

        this.retrievalService = retrievalService;
        this.ollamaClient = ollamaClient;
    }

    public String answer(String question) {

        List<RetrievalResult> results = retrievalService.retrieve(question, 3);

        String context = results.stream().map(result -> result.document().text()).collect(Collectors.joining("\n\n"));


        String prompt = """
                Answer the question using the banking policy information below.
                
                BANKING POLICY:
                %s
                
                QUESTION:
                %s
                
                Give a direct and concise answer.
                """.formatted(context, question);


        return ollamaClient.ask(prompt);
    }
}
