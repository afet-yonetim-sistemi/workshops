package com.ays.microservices.dtos;

import java.time.LocalDateTime;
import java.util.Map;

import com.ays.microservices.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {
    private String to;
    private String subject;
    private String template;
    private Map<String, Object> templateData;  // Problem: Raw object storage
    private Order order;  // Problem: Direct entity reference
    private LocalDateTime createdAt;
    private int retryCount;
}