package com.ays.microservices.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ShippingInfo {
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;  // Duplicate of customer phone

    @Enumerated(EnumType.STRING)
    private ShippingMethod method;

    private String trackingNumber;
    private LocalDateTime estimatedDelivery;

    // Getters, setters...
}