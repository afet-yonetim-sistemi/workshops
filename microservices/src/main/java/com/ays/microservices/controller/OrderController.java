package com.ays.microservices.controller;

import com.ays.microservices.dtos.OrderRequest;
import com.ays.microservices.dtos.OrderResponse;
import com.ays.microservices.model.Order;
import com.ays.microservices.service.OrderService;
import com.ays.microservices.utils.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        // No input validation
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(orderMapper.toResponse(order));  // Exposing full entity data
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        // No security check
        Order order = orderService.findById(id);
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }
}