package com.ays.microservices.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;  // Security issue: exposing full address
    private List<String> paymentMethods;  // Security issue: exposing payment methods
}