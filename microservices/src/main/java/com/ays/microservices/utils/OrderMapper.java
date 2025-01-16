package com.ays.microservices.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.ays.microservices.dtos.CustomerDetails;
import com.ays.microservices.dtos.OrderItemDetails;
import com.ays.microservices.dtos.OrderRequest;
import com.ays.microservices.dtos.OrderResponse;
import com.ays.microservices.model.Address;
import com.ays.microservices.model.Customer;
import com.ays.microservices.model.Order;
import com.ays.microservices.model.OrderItem;
import com.ays.microservices.model.PaymentDetails;
import com.ays.microservices.model.Product;
import com.ays.microservices.repository.CustomerRepository;
import com.ays.microservices.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    @Autowired
    private CustomerRepository customerRepository;  // Problem: Repository in mapper
    @Autowired
    private ProductRepository productRepository;    // Problem: Too many dependencies

    public Order toEntity(OrderRequest request) {
        Order order = new Order();

        // Problem: Direct repository call in mapper
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);
        order.setCustomerEmail(customer.getEmail());    // Problem: Duplicating data
        order.setCustomerPhone(customer.getPhone());    // Problem: Duplicating data

        // Problem: Complex business logic in mapper
        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    OrderItem item = new OrderItem();
                    // Problem: Another repository call
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    item.setProduct(product);
                    item.setQuantity(itemRequest.getQuantity());
                    item.setPrice(product.getPrice());
                    return item;
                })
                .collect(Collectors.toList());

        order.setItems(items);

        // Problem: Business logic in mapper
        order.calculateTotals();

        return order;
    }

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());

        // Problem: Exposing too much customer info
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setId(order.getCustomer().getId());
        customerDetails.setName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        customerDetails.setEmail(order.getCustomer().getEmail());
        customerDetails.setPhone(order.getCustomer().getPhone());
        customerDetails.setAddress(order.getCustomer().getAddresses().stream()
                .filter(Address::getIsDefault)
                .findFirst()
                .map(addr -> addr.getStreet() + ", " + addr.getCity())
                .orElse(""));  // Problem: Complex transformation logic

        customerDetails.setPaymentMethods(order.getCustomer().getPaymentMethods().stream()
                .map(pm -> "****" + pm.getCardNumber().substring(pm.getCardNumber().length() - 4))
                .collect(Collectors.toList()));  // Problem: Processing sensitive data

        response.setCustomer(customerDetails);

        // Problem: Mapping items with complex logic
        response.setItems(order.getItems().stream()
                .map(item -> {
                    OrderItemDetails itemDetails = new OrderItemDetails();
                    itemDetails.setProductId(item.getProduct().getId());
                    itemDetails.setProductName(item.getProduct().getName());
                    itemDetails.setProductImage(item.getProduct().getImages().get(0).getUrl()); // What if?
                    itemDetails.setQuantity(item.getQuantity());
                    itemDetails.setPrice(item.getPrice());
                    itemDetails.setSubtotal(item.getPrice() * item.getQuantity());  // Business calculation in mapper
                    return itemDetails;
                })
                .collect(Collectors.toList()));

        // Problem: Exposing payment details
        PaymentDetails paymentDetails = new PaymentDetails();
        if (order.getPayment() != null) {
            paymentDetails.setMaskedCardNumber("****-****-****-" +
                    order.getPayment().getCardNumber().substring(order.getPayment().getCardNumber().length() - 4));
            paymentDetails.setStatus(order.getPayment().getStatus());
            paymentDetails.setPaymentDate(order.getPayment().getPaymentDate());
            paymentDetails.setTransactionId(order.getPayment().getTransactionId());
        }
        response.setPayment(paymentDetails);

        // Problem: Copying shipping info directly
        response.setShipping(order.getShipping());

        response.setStatus(order.getOrderStatus());
        response.setTotalAmount(order.getTotalAmount());

        return response;
    }
}