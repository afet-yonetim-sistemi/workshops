package com.ays.microservices.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String cardNumber;          // Problem: Sensitive data not encrypted
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;                 // Problem: Should never store CVV

    private Double amount;
    private String currency;
    private String transactionId;
    private String paymentProvider;     // STRIPE/PAYPAL etc.

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime paymentDate;
    private String errorMessage;

    public Payment(String id, Double amount, String success) {
        this.transactionId = id;
        this.amount = amount;
        this.status = PaymentStatus.valueOf(success);
    }

    // Problem: Business logic in entity
    public boolean isRefundable() {
        return status == PaymentStatus.SUCCESS
                && ChronoUnit.DAYS.between(paymentDate, LocalDateTime.now()) <= 30;
    }

    // Getters, setters...
}