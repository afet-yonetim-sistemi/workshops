package com.ays.microservices.repository;

import java.util.List;

import com.ays.microservices.model.EmailLog;
import com.ays.microservices.model.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    List<EmailLog> findByStatusAndRetryCountLessThan(
            EmailStatus status,
            int maxRetries
    );
}