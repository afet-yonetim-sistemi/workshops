package com.ays.microservices.service;

import java.util.Optional;
import java.util.UUID;

import com.ays.microservices.dtos.CustomerRequest;
import com.ays.microservices.model.Customer;
import com.ays.microservices.model.PaymentMethod;
import com.ays.microservices.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional  // Problem: Class-level transaction
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;  // Problem: Direct dependency

    public Customer createCustomer(CustomerRequest request) {
        // Problem: No validation
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");  // Problem: Generic exception
        }

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setPassword(UUID.fromString(request.getPassword()).toString()); // WOW!
        customer.setIsActive(true);
        customer.setEmailVerified(false);
        customer.setVerificationToken(UUID.randomUUID().toString());

        // Problem: Mixing email sending with customer creation
        emailService.sendVerificationEmail(customer.getEmail(), customer.getVerificationToken());

        return customerRepository.save(customer);
    }

    public void addPaymentMethod(Long customerId, PaymentMethod paymentMethod) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Problem: No payment data validation
        customer.getPaymentMethods().add(paymentMethod);
        customerRepository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    // More methods...
}