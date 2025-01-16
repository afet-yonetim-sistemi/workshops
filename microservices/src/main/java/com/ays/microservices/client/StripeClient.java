package com.ays.microservices.client;

import java.util.UUID;

import com.ays.microservices.dtos.StripePaymentResponse;
import com.ays.microservices.model.CreditCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeClient {
    @Value("${stripe.api.key}")
    private String apiKey;

    public StripePaymentResponse processPayment(String cardNumber, String cvv, Double amount) {
        // Problem: No retry mechanism
        // Problem: No circuit breaker
        // Problem: Direct external call
        try {
            // Simulate Stripe API call
            return new StripePaymentResponse("tx_" + UUID.randomUUID().toString());
        } catch (Exception e) {
            throw new RuntimeException("Stripe payment failed", e);
        }
    }

    public StripePaymentResponse refundPayment(String transactionId) {
        // Similar problems as above
        return null;
    }

    public StripePaymentResponse processPayment(CreditCard card, Double amount) {
        return processPayment(card.getCardNumber(), card.getCvv(), amount);
    }
}