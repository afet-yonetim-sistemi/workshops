package com.ays.microservices.repository;

import java.util.List;

import com.ays.microservices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Problem: Too many specific queries
    List<Product> findByCategoryIdAndActiveTrueAndStockQuantityGreaterThan(
            Long categoryId, Integer minStock);

    List<Product> findByPriceBetweenAndActiveTrue(Double minPrice, Double maxPrice);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> search(@Param("keyword") String keyword);

    // Problem: N+1 query potential
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.category.id = :categoryId")
    List<Product> findByCategoryWithImages(@Param("categoryId") Long categoryId);

    List<Product> findByActiveTrue();
}