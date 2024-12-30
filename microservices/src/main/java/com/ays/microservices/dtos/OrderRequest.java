package com.ays.microservices.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long customerId;
    private List<OrderItemRequest> items;
    private String shippingAddress;
    private String paymentMethod;
    private String couponCode;  // Mixed concern: Discount logic mixed with order
}