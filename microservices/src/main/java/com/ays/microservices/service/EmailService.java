package com.ays.microservices.service;

import com.ays.microservices.model.Order;
import com.ays.microservices.model.Payment;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;  // Problem: Direct dependency on mail implementation

    @Value("${spring.mail.username}")
    private String fromEmail;           // Problem: Configuration in service

    @Autowired
    private TemplateEngine templateEngine;  // Problem: Template processing mixed with sending

    // Problem: Synchronous email sending
    public void sendOrderConfirmation(String to, Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Order Confirmation #" + order.getId());

            // Problem: Template processing in service
            Context context = new Context();
            context.setVariable("order", order);
            context.setVariable("customer", order.getCustomer());
            String content = templateEngine.process("order-confirmation", context);

            helper.setText(content, true);
            mailSender.send(message);  // Problem: Blocking operation

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);  // Problem: Generic exception
        }
    }

    // Problem: Code duplication across different email types
    public void sendPaymentConfirmation(String to, Payment payment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Payment Confirmation #" + payment.getTransactionId());

            Context context = new Context();
            context.setVariable("payment", payment);
            context.setVariable("order", payment.getOrder());
            String content = templateEngine.process("payment-confirmation", context);

            helper.setText(content, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendVerificationEmail(String email, String verificationToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("Your email has been verified! #" + verificationToken);

            Context context = new Context();
            context.setVariable("verification", verificationToken);
            String content = templateEngine.process("verification-confirmation", context);

            helper.setText(content, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}