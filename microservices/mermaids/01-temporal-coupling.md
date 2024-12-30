```mermaid
    sequenceDiagram
    participant User
    participant OrderService
    participant PaymentService
    participant InventoryService
    
        Note over OrderService,InventoryService: Problem: Temporal Coupling
        
        User->>+OrderService: Create Order
        OrderService->>+PaymentService: Process Payment (Sync HTTP)
        Note right of PaymentService: If service is down,<br/>entire flow fails
        PaymentService-->>-OrderService: Payment Response
        
        OrderService->>+InventoryService: Check Stock (Sync HTTP)
        Note right of InventoryService: High latency due to<br/>chain of calls
        InventoryService-->>-OrderService: Stock Response
        
        OrderService-->>-User: Order Response
```

# Solution strategies
1. Caching [01-temporal-coupling-solution-cache.md](01-temporal-coupling-solution-cache.md)
2. Broker [01-temporal-coupling-solution-broker.md](01-temporal-coupling-solution-broker.md)

