package com.ays.microservices.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Boolean isDefault;
    private String addressType;  // HOME/WORK
    private LocalDateTime createdAt;  // Unnecessary temporal data
    private LocalDateTime updatedAt;  // Unnecessary temporal data

    // Problem: Exposes internal ID that could be used maliciously
    public Long getId() {
        return id;
    }
}