package com.learnai.ollama;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final OllamaClient ollamaClient;
    private final BankingTools bankingTools;
    private final ObjectMapper objectMapper;
    private final DocumentService documentService;

    public ChatController(OllamaClient ollamaClient, BankingTools bankingTools, ObjectMapper objectMapper, DocumentService documentService) {

        this.ollamaClient = ollamaClient;
        this.bankingTools = bankingTools;
        this.objectMapper = objectMapper;
        this.documentService = documentService;
    }

//    @GetMapping("/tool")
//    public String tool(@RequestParam String message) {
//
//        String prompt = """
//                You are a banking assistant.
//
//                You have access to the following tools:
//
//                1. get_balance
//                   Description: Get the customer's current account balance.
//                   Argument: accountId
//
//                2. get_transactions
//                   Description: Get the customer's recent transactions.
//                   Argument: accountId
//
//                3. get_payment_status
//                   Description: Check the status of a payment.
//                   Argument: transactionId
//
//                4. get_credit_card_limit
//                   Description: Get the customer's credit card limit and available limit.
//                   Argument: accountId
//
//                Your job is to decide which tool should be used
//                to answer the user's request.
//
//                Return ONLY JSON in this format:
//
//                {
//                  "tool": "tool_name",
//                  "argument": "value"
//                }
//
//                User request:
//                %s
//                """.formatted(message);
//
//        try {
//
//            // 1. Ask LLM which tool to use
//            String llmResponse = ollamaClient.ask(prompt);
//
//            System.out.println("LLM response: " + llmResponse);
//
//            // 2. Convert JSON into Java object
//            ToolCall toolCall =
//                    objectMapper.readValue(llmResponse, ToolCall.class);
//
//            // 3. Execute the selected tool
//            String result = executeTool(toolCall);
//
//            String finalPrompt = """
//                    You are a banking assistant.
//
//                    The user asked:
//                    %s
//
//                    We executed the following tool:
//
//                    Tool:
//                    %s
//
//                    Tool result:
//                    %s
//
//                    Using the tool result, answer the user's question
//                    clearly and concisely.
//
//                    Do not mention internal tools or implementation details.
//                    """.formatted(
//                    message,
//                    toolCall.getTool(),
//                    result
//            );
//
//            System.out.println("final Prompt: " + finalPrompt);
//
//            return ollamaClient.ask(finalPrompt);
//
//        } catch (Exception e) {
//
//            return "Error: " + e.getMessage();
//        }
//    }

    @GetMapping("/native-tool")
    public String nativeTool(@RequestParam String message) {

        System.out.println("request param: " + message);

        List<Map<String, Object>> messages = new ArrayList<>();

        messages.add(Map.of("role", "user", "content", message));

        // First LLM call
        OllamaResponse response = ollamaClient.chat(messages, ToolDefinitions.getTools());
        System.out.println("First LLM Response: " + response);
        List<ToolCall> toolCalls = response.getMessage().getTool_calls();

        if (toolCalls == null || toolCalls.isEmpty()) {

            return response.getMessage().getContent();
        }

        // Execute each requested tool
        for (ToolCall toolCall : toolCalls) {

            String toolResult = executeNativeTool(toolCall);

            System.out.println("Tool: " + toolCall.getFunction().getName());

            System.out.println("Result: " + toolResult);

            // Add assistant's tool-call message
            messages.add(Map.of("role", "assistant", "tool_calls", List.of(Map.of("function", Map.of("name", toolCall.getFunction().getName(), "arguments", toolCall.getFunction().getArguments())))));

            // Add tool result
            messages.add(Map.of("role", "tool", "content", toolResult));
        }

        // Second LLM call
        OllamaResponse finalResponse = ollamaClient.chat(messages, ToolDefinitions.getTools());

        return finalResponse.getMessage().getContent();
    }

    private String executeNativeTool(ToolCall toolCall) {

        String toolName = toolCall.getFunction().getName();

        Map<String, Object> arguments = toolCall.getFunction().getArguments();

        return switch (toolName) {

            case "get_balance" -> bankingTools.getBalance((String) arguments.get("accountId"));

            case "get_transactions" -> bankingTools.getTransactions((String) arguments.get("accountId"));

            case "get_payment_status" -> bankingTools.getPaymentStatus((String) arguments.get("transactionId"));

            case "get_credit_card_limit" -> bankingTools.getCreditCardLimit((String) arguments.get("accountId"));

            default -> throw new IllegalArgumentException("Unknown tool: " + toolName);
        };
    }

    @GetMapping("/rag")
    public String rag(@RequestParam String message) {

        String document;

        try {

            document = documentService.getRefundPolicy();

        } catch (IOException e) {

            return "Unable to read document.";
        }

        String prompt = """
                You are a banking assistant.
                
                Answer the user's question using ONLY the
                information contained in the following document.
                
                DOCUMENT:
                %s
                
                USER QUESTION:
                %s
                
                If the document does not contain the answer,
                say that the information is not available.
                """.formatted(document, message);

        return ollamaClient.ask(prompt);
    }
//    private String executeTool(ToolCall toolCall) {
//
//        return switch (toolCall.getTool()) {
//
//            case "get_balance" -> bankingTools.getBalance(toolCall.getArgument());
//
//            case "get_transactions" -> bankingTools.getTransactions(toolCall.getArgument());
//
//            case "get_payment_status" -> bankingTools.getPaymentStatus(toolCall.getArgument());
//
//            case "get_credit_card_limit" -> bankingTools.getCreditCardLimit(toolCall.getArgument());
//
//            default -> "Unknown tool: " + toolCall.getTool();
//        };
//    }
}