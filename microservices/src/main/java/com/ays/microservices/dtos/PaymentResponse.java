package com.ays.microservices.dtos;

import java.time.LocalDateTime;

import com.ays.microservices.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;            // Problem: Exposing internal ID
    private String maskedCardNumber;
    private String cardHolderName;     // Problem: Exposing PII
    private PaymentStatus status;
    private Double amount;
    private String transactionId;
    private LocalDateTime paymentDate;
}