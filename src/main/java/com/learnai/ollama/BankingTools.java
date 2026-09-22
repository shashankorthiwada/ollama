package com.learnai.ollama;

import org.springframework.stereotype.Service;

@Service
public class BankingTools {

    public String getBalance(String accountId) {

        return """
                Account: %s
                Balance: ₹1,25,000
                """.formatted(accountId);
    }

    public String getTransactions(String accountId) {

        return """
                Recent transactions for %s:

                1. Amazon       - ₹2,500
                2. Swiggy       - ₹650
                3. Electricity - ₹1,200
                """.formatted(accountId);
    }

    public String getPaymentStatus(String transactionId) {

        return """
                Transaction: %s
                Status: FAILED
                Reason: Insufficient funds
                """.formatted(transactionId);
    }

    public String getCreditCardLimit(String accountId) {

        return """
            Account: %s
            Credit Card Limit: ₹2,00,000
            Available Limit: ₹1,35,000
            """.formatted(accountId);
    }
}
