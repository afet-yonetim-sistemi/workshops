package com.ays.microservices.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ays.microservices.dtos.EmailMessage;
import com.ays.microservices.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailQueue {
    private final BlockingQueue<EmailMessage> queue = new LinkedBlockingQueue<>();

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 1000)  // Problem: Fixed rate processing
    public void processQueue() {
        EmailMessage email = queue.poll();
        if (email != null) {
            try {
                // Problem: Still synchronous processing
                emailService.sendOrderConfirmation(email.getTo(), email.getOrder());
            } catch (Exception e) {
                // Problem: No proper error handling or retry
                log.error("Failed to send email", e);
            }
        }
    }
}