package com.ays.microservices.dtos;

import lombok.Data;

@Data
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;  // Problem: Plain text password in DTO
    private AddressRequest primaryAddress;
}