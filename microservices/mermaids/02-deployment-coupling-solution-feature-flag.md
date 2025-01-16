```java
@Service
public class OrderService {
    @Autowired
    private FeatureToggleService featureFlags;

    public void processOrder(Order order) {
        if (featureFlags.isEnabled("NEW_ORDER_PROCESS")) {
            // New implementation
            newOrderProcess(order);
        } else {
            // Old implementation
            legacyOrderProcess(order);
        }
    }
}

// Configuration
feature.flags:
        NEW_ORDER_PROCESS: true
        ENHANCED_PAYMENT: false

```

# Warning!
Keep flags temporary \
Clean up old code \
Monitor flag usage \
Document feature flags