package com.ays.microservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDetails {
    private Long productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private Double price;
    private Double subtotal;
}