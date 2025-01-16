package com.ays.microservices.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;  // Problem: Sensitive data in entity

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)  // Problem: Eager loading
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CreditCard> creditCards = new ArrayList<>();

    private LocalDateTime lastLogin;
    private Boolean isActive;
    private String verificationToken;
    private Boolean emailVerified;

    // Problem: Business logic in entity
    public boolean canPlaceOrder() {
        return isActive && emailVerified && !paymentMethods.isEmpty();
    }

    // Problem: Mixing concerns
    public void updatePassword(String newPassword) {
        this.password = newPassword + UUID.randomUUID();
    }
}