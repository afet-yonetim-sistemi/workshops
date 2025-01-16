package com.ays.microservices.dtos;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodResponse {
    private Long id;
    private String cardNumber;  // Problem: Even masked card number is sensitive
    private String cardHolder;
    private String expiryMonth;
    private String expiryYear;
    private String cardType;    // VISA/MASTERCARD etc.
    private Boolean isDefault;
    private LocalDateTime addedOn;     // Unnecessary temporal data
    private LocalDateTime updatedAt;   // Unnecessary temporal data

    // Bad practice: Exposing too much payment info
    public String getCardNumber() {
        // Even with masking, still exposing real card digits
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    public String getExpiryMonth() {
        // Sensitive data exposure
        return expiryMonth;
    }

    public String getExpiryYear() {
        // Sensitive data exposure
        return expiryYear;
    }

    // Standard getters and setters...
}