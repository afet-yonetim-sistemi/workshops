package com.ays.microservices.controller;

import com.ays.microservices.dtos.PaymentRequest;
import com.ays.microservices.dtos.PaymentResponse;
import com.ays.microservices.model.Payment;
import com.ays.microservices.service.PaymentService;
import com.ays.microservices.utils.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        // Problem: No input validation
        Payment payment = paymentService.processPayment(request);
        return ResponseEntity.ok(PaymentMapper.toResponse(payment));
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(@PathVariable Long id) {
        // Problem: No security check
        Payment payment = paymentService.refundPayment(id);
        return ResponseEntity.ok(PaymentMapper.toResponse(payment));
    }
}