package com.ays.microservices.utils;

import com.ays.microservices.dtos.PaymentResponse;
import com.ays.microservices.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public static PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());
        // Problem: Even masked card number is sensitive
        response.setMaskedCardNumber("****-****-****-" +
                payment.getCardNumber().substring(payment.getCardNumber().length() - 4));
        response.setCardHolderName(payment.getCardHolderName());
        response.setStatus(payment.getStatus());
        response.setAmount(payment.getAmount());
        response.setTransactionId(payment.getTransactionId());
        response.setPaymentDate(payment.getPaymentDate());
        return response;
    }
}
