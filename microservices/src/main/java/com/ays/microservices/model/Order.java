package com.ays.microservices.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)  // Problem 1: Eager loading
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)  // Problem 2: Too many items in one transaction
    private List<OrderItem> items;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    private ShippingInfo shipping;

    private String customerEmail;  // Problem 3: Duplicate data
    private String customerPhone;  // Problem 4: Data redundancy

    private Double totalAmount;
    private Double taxAmount;
    private Double shippingCost;

    private OrderStatus orderStatus;

    // Problem 5: Business logic in entity
    public void calculateTotals() {
        this.totalAmount = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        this.taxAmount = totalAmount * 0.18;  // Problem 6: Hardcoded business rules
        this.shippingCost = totalAmount > 1000 ? 0 : 25.99;
    }
}