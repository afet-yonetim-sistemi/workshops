# Solutions for microservices

## Circuit Breaker:
✅ Prevents system overload \
✅ Quick failure detection \
❌ Configuration complexity \
❌ State management needed
```java
@Service
public class OrderService {
    @CircuitBreaker(name = "paymentService", 
                    fallbackMethod = "fallbackPayment")
    public PaymentResponse processPayment(Order order) {
        return paymentService.process(order);
    }
    
    public PaymentResponse fallbackPayment(Order order, Exception e) {
        // Use cached data or default response
        return PaymentResponse.builder()
            .status(PENDING)
            .message("Payment will be processed later")
            .build();
    }
}
```

## CQRS:

✅ Optimized read/write \
✅ Scalability \
❌ Complexity \
❌ Eventual consistency
```java
// Command (Write) Side
@Service
public class OrderCommandService {
    public void createOrder(CreateOrderCommand cmd) {
        // Async process for write operations
        kafkaTemplate.send("orders", cmd);
    }
}

// Query (Read) Side
@Service
public class OrderQueryService {
    public OrderView getOrder(String orderId) {
        // Fast read from cache or read replica
        return orderViewRepository.findById(orderId);
    }
}
```

## Bulkhead:
✅ Resource isolation \
✅ Failure containment \
❌ Resource overhead \
❌ Capacity planning needed

## Event Sourcing:
- Store events instead of current state, then rebuild the state from events