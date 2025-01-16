package com.ays.microservices.dtos;

import java.util.List;

import com.ays.microservices.model.Address;
import com.ays.microservices.model.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<Address> addresses;  // Problem: Exposing all addresses
    private List<PaymentMethodResponse> paymentMethods;  // Problem: Exposing payment info

    public static CustomerResponse from(Customer customer) {
        return CustomerResponse.builder()
                .addresses(customer.getAddresses())
                .email(customer.getEmail())
                .firstName(customer.getFirstName()) // Fed up here
                .build();
    }
}