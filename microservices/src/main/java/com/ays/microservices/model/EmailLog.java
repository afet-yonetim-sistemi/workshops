package com.ays.microservices.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "email_logs")
@Data
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient")
    private String recipient;
    private String subject;
    private String template;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private LocalDateTime sentAt;
    private String errorMessage;
    private Integer retryCount;

    //@Column(columnDefinition = "VARCHAR(32672)")
    private String templateData;
}