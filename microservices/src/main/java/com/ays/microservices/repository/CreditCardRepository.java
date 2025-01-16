package com.ays.microservices.repository;

import java.util.List;
import java.util.Optional;

import com.ays.microservices.model.CreditCard;
import com.ays.microservices.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findAllByCustomer(Customer customer);

    Optional<CreditCard> findFirstByCustomer(Customer customer);
}