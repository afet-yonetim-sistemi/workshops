package com.ays.microservices.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String sku;
    private Double price;
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.EAGER)  // Problem: Eager loading
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @ElementCollection
    @CollectionTable(name = "product_attributes")
    private Map<String, String> attributes;  // Problem: Unstructured data

    private Boolean active;
    private Double weight;
    private String dimensions;  // Problem: Storing as string "LxWxH"

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Problem: Business logic in entity
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean canOrder(int quantity) {
        return stockQuantity >= quantity;
    }
}