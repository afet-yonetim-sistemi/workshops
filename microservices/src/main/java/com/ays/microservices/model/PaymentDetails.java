package com.ays.microservices.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "payment_details")
@Getter
@Setter
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;             // Problem: Tight coupling with Order

    private String cardNumber;       // Problem: Sensitive data not encrypted
    private String cardHolderName;   // Problem: PII not encrypted
    private String expiryMonth;
    private String expiryYear;
    private String cvv;             // Problem: Should NEVER store CVV

    private Double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentMethod;    // CREDIT_CARD, PAYPAL, etc.
    private String paymentProvider;  // STRIPE, PAYPAL, etc.
    private String transactionId;    // External payment provider's transaction ID

    private String maskedCardNumber;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    private String errorMessage;     // Problem: Storing error details in same entity
    private Integer retryCount;      // Problem: Payment logic details in entity

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Problem: Business logic in entity
    public boolean isRefundable() {
        if (status != PaymentStatus.SUCCESS) {
            return false;
        }
        // Problem: Hardcoded business rule
        return ChronoUnit.DAYS.between(paymentDate, LocalDateTime.now()) <= 30;
    }

    // Problem: Payment validation in entity
    public boolean isValid() {
        return amount > 0
                && paymentDate != null
                && cardNumber != null
                && cardNumber.length() >= 13;
    }

    // Problem: Logic for masking card number in entity
    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) {
            return null;
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

}