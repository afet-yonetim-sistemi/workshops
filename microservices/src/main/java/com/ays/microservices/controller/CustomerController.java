package com.ays.microservices.controller;

import com.ays.microservices.dtos.CustomerRequest;
import com.ays.microservices.dtos.CustomerResponse;
import com.ays.microservices.model.Customer;
import com.ays.microservices.model.PaymentMethod;
import com.ays.microservices.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        // Problem: No input validation
        Customer customer = customerService.createCustomer(request);
        return ResponseEntity.ok(CustomerResponse.from(customer));  // Problem: Exposing full entity
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        // Problem: No security check
        Customer customer = customerService.findById(id).orElseThrow(); // ??
        return ResponseEntity.ok(CustomerResponse.from(customer));
    }

    @PutMapping("/{id}/payment-methods")
    public ResponseEntity<Void> addPaymentMethod(
            @PathVariable Long id,
            @RequestBody PaymentMethod paymentMethod) {  // Problem: Accepting entity directly
        customerService.addPaymentMethod(id, paymentMethod);
        return ResponseEntity.ok().build();
    }
}