package com.ays.microservices.repository;

import java.util.List;

import com.ays.microservices.model.Payment;
import com.ays.microservices.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Problem: Complex queries in repository
    @Query("SELECT p FROM Payment p JOIN FETCH p.order WHERE p.status = :status")
    List<Payment> findAllByStatusWithOrder(@Param("status") PaymentStatus status);

    List<Payment> findByOrderCustomerId(Long customerId);  // Problem: N+1 query potential
}