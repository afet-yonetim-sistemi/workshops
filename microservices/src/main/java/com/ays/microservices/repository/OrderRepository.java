package com.ays.microservices.repository;

import java.util.List;

import com.ays.microservices.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Problem 19: Complex queries in repository
    @Query("SELECT o FROM Order o JOIN FETCH o.customer JOIN FETCH o.items WHERE o.customer.id = :customerId")
    List findAllByCustomerWithItems(@Param("customerId") Long customerId);

    // Problem 20: N+1 query problem waiting to happen
    List findByCustomerEmailAndPaymentStatus(String email, String status);
}