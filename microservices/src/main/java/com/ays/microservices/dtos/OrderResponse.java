package com.ays.microservices.dtos;

import java.util.List;

import com.ays.microservices.model.OrderStatus;
import com.ays.microservices.model.PaymentDetails;
import com.ays.microservices.model.ShippingInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private CustomerDetails customer;  // Contains sensitive info
    private List<OrderItemDetails> items;
    private PaymentDetails payment;  // Contains sensitive info
    private ShippingInfo shipping;
    private OrderStatus status;
    private Double totalAmount;
}