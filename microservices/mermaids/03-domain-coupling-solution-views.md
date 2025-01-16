```mermaid
classDiagram
    direction LR
    class StockView {
        +Long orderId
        +List~StockItem~ items
        +checkStock()
    }

    class StockItem {
        +String productId
        +int quantity
    }

    class PaymentView {
        +Long orderId
        +double totalAmount
        +String cardToken
        +processPayment()
    }

    class ShippingView {
        +Long orderId
        +Address deliveryAddress
        +double totalWeight
        +calculateShipping()
    }

    StockView --> StockItem

    note for StockView "Sadece stok için\ngereken bilgiler"
    note for PaymentView "Sadece ödeme için\ngereken bilgiler"
    note for ShippingView "Sadece kargo için\ngereken bilgiler"
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

// Çözüm 1: View kullanımı
public class StockView {
    private final Long orderId;
    private final List<StockItem> items;  // Sadece ürün ID ve adet

    public boolean checkStock() {
        // Minimum bilgi ile stok kontrolü
        return stockService.check(items);
    }
}
```