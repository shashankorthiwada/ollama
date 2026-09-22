package com.learnai.ollama;

import java.util.List;
import java.util.Map;

public class ToolDefinitions {

    public static List<Map<String, Object>> getTools() {

        return List.of(

                Map.of(
                        "type", "function",
                        "function", Map.of(
                                "name", "get_balance",
                                "description",
                                "Get the customer's current account balance.",
                                "parameters", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "accountId", Map.of(
                                                        "type", "string",
                                                        "description", "Customer account ID"
                                                )
                                        ),
                                        "required", List.of("accountId")
                                )
                        )
                ),

                Map.of(
                        "type", "function",
                        "function", Map.of(
                                "name", "get_transactions",
                                "description",
                                "Get the customer's recent transactions.",
                                "parameters", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "accountId", Map.of(
                                                        "type", "string",
                                                        "description", "Customer account ID"
                                                )
                                        ),
                                        "required", List.of("accountId")
                                )
                        )
                ),

                Map.of(
                        "type", "function",
                        "function", Map.of(
                                "name", "get_payment_status",
                                "description",
                                "Check the status of a payment.",
                                "parameters", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "transactionId", Map.of(
                                                        "type", "string",
                                                        "description", "Transaction ID"
                                                )
                                        ),
                                        "required", List.of("transactionId")
                                )
                        )
                ),

                Map.of(
                        "type", "function",
                        "function", Map.of(
                                "name", "get_credit_card_limit",
                                "description",
                                "Get the customer's credit card limit and available limit.",
                                "parameters", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "accountId", Map.of(
                                                        "type", "string",
                                                        "description", "Customer account ID"
                                                )
                                        ),
                                        "required", List.of("accountId")
                                )
                        )
                )
        );
    }
}
