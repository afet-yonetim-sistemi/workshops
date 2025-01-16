package com.ays.microservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private String cardNumber;
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;                // Problem: Sensitive data in DTO
    private Double amount;
    private String currency;
}