package com.ays.microservices.service;

import com.ays.microservices.dtos.OrderItemRequest;
import com.ays.microservices.dtos.OrderRequest;
import com.ays.microservices.model.CreditCard;
import com.ays.microservices.model.Customer;
import com.ays.microservices.model.Order;
import com.ays.microservices.model.OrderItem;
import com.ays.microservices.model.Payment;
import com.ays.microservices.model.Product;
import com.ays.microservices.repository.CreditCardRepository;
import com.ays.microservices.repository.CustomerRepository;
import com.ays.microservices.repository.OrderRepository;
import com.ays.microservices.repository.PaymentRepository;
import com.ays.microservices.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  // Problem 7: Class-level transaction
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;  // Problem 8: Too many dependencies
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PaymentService paymentService;

    public Order createOrder(OrderRequest request) {
        // Problem 9: One giant method doing everything
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setCustomerEmail(customer.getEmail());  // Problem 10: Copying data
        order.setCustomerPhone(customer.getPhone());

        // Problem 11: Complex business logic mixed with data access
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock");
            }

            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);  // Problem 12: Multiple DB operations

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice());
            order.getItems().add(item);
        }

        order.calculateTotals();

        // Problem 13: Direct payment processing in order flow
        Payment payment = paymentService.processPayment(
                getDefaultCardOfCustomer(customer),
                order.getTotalAmount()
        );
        order.setPayment(payment);

        Order savedOrder = (Order) orderRepository.save(order);

        // Problem 14: Synchronous email sending
        emailService.sendOrderConfirmation(customer.getEmail(), savedOrder);

        return savedOrder;
    }

    private CreditCard getDefaultCardOfCustomer(Customer customer) {
        return creditCardRepository.findFirstByCustomer(customer).orElseThrow();
    }

    public Order findById(Long id) {
        return null;
    }
}