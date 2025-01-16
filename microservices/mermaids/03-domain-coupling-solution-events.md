```mermaid
sequenceDiagram
    participant OrderService
    participant EventBus
    participant StockService
    participant PaymentService
    participant ShippingService

    OrderService->>EventBus: StockCheckEvent(orderId, items)
    EventBus->>StockService: Handle Stock Check
    StockService-->>EventBus: StockConfirmedEvent

    OrderService->>EventBus: PaymentRequestEvent(amount, token)
    EventBus->>PaymentService: Handle Payment
    PaymentService-->>EventBus: PaymentCompletedEvent

    OrderService->>EventBus: ShippingRequestEvent(address, weight)
    EventBus->>ShippingService: Handle Shipping
    ShippingService-->>EventBus: ShippingArrangedEvent

    note over OrderService,EventBus: Her servis sadece kendi domain bilgisini alır
```

```java
// Problem durumu
public class Order {
    private Customer customer; // Tüm müşteri bilgileri
    private PaymentInfo paymentInfo; // Hassas ödeme bilgileri
    private List<CartItem> items; // Sepet detayları

    // Stok kontrolü için fazla detay içeriyor
    public void checkStock() {
        stockService.check(this); // Tüm order objesi gönderiliyor
    }
}


// Çözüm 2: Event kullanımı
public class StockCheckEvent {
    private final Long orderId;
    private final List<StockItem> items;
    // Hassas bilgiler yok
}

@Service
public class StockService {
    @EventListener
    public void onStockCheck(StockCheckEvent event) {
        // Sadece stok için gereken bilgilerle çalışır
        checkAndUpdateStock(event.getItems());
    }
}
```