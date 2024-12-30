package com.ays.microservices.service;

import com.ays.microservices.client.StripeClient;
import com.ays.microservices.dtos.PaymentRequest;
import com.ays.microservices.dtos.StripePaymentResponse;
import com.ays.microservices.model.CreditCard;
import com.ays.microservices.model.Payment;
import com.ays.microservices.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {
    @Value("${stripe.secret.key}")  // Problem 21: Sensitive data in code
    private String stripeKey;

    @Autowired
    private StripeClient stripeClient;

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(CreditCard card, Double amount) {
        try {
            // Problem 22: Direct external service call
            // Problem 23: No retry mechanism
            // Problem 24: No circuit breaker
            StripePaymentResponse session = stripeClient.processPayment(card, amount);

            if (session.isSuccess()) {
                return new Payment(session.getId(), amount, "SUCCESS");
            } else {
                throw new RuntimeException("Payment failed");  // Problem 25: Generic exception
            }
        } catch (Exception e) {
            log.error("Payment failed", e);  // Problem 26: Inadequate error handling
            throw new RuntimeException("Payment processing failed", e);
        }
    }

    public Payment processPayment(PaymentRequest request) {
        return processPayment(new CreditCard(request.getCardNumber(), request.getCardHolderName(), request.getCvv()), request.getAmount()); // Don't do this at home!
    }

    public Payment refundPayment(Long id) {
        Payment request = paymentRepository.getReferenceById(id);
        return processPayment(new CreditCard(request.getCardNumber(), request.getCardHolderName(), request.getCvv()), request.getAmount()); // Don't do this at home!
    }
}