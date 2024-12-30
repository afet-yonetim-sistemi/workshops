package com.ays.microservices.dtos;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private String specialInstructions;  // Mixed concern: Delivery instructions in item

    // Standard getters, setters
}