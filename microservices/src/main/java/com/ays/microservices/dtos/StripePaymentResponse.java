package com.ays.microservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StripePaymentResponse {
    private boolean success;
    private String id; //what id?

    public StripePaymentResponse(String transactionDetails) {

    }
}
