package com.ays.microservices.model;

import java.time.LocalDateTime;
import java.time.YearMonth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "credit_cards")
@Data
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String cardNumber;          // Problem: Sensitive data not encrypted
    private String cardHolderName;      // Problem: PII data not encrypted
    private String expiryMonth;
    private String expiryYear;
    private String cvv;                 // Problem: Should NEVER store CVV
    private String cardType;            // VISA, MASTERCARD, etc.
    private Boolean isDefault;

    @Column(name = "is_active")
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CreditCard(String cardNumber, String cardHolderName, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
    }

    public CreditCard() {

    }

    // Problem: Business logic in entity
    public boolean isExpired() {
        YearMonth expiry = YearMonth.of(
                Integer.parseInt("20" + expiryYear),
                Integer.parseInt(expiryMonth)
        );
        return YearMonth.now().isAfter(expiry);
    }

    // Problem: Validation logic in entity
    public boolean isValid() {
        return active && !isExpired() && validateLuhn();
    }

    // Problem: Business logic in entity
    private boolean validateLuhn() {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}