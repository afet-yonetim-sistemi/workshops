package com.ays.microservices.repository;

import java.util.List;
import java.util.Optional;

import com.ays.microservices.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Problem: Complex queries in repository
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.addresses LEFT JOIN FETCH c.paymentMethods WHERE c.email = :email")
    Optional<Customer> findByEmailWithAddressesAndPayments(@Param("email") String email);

    // Problem: N+1 query potential
    List<Customer> findByIsActiveAndEmailVerified(Boolean isActive, Boolean emailVerified);

    boolean existsByEmail(String email);
}