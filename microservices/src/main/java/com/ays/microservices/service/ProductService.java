package com.ays.microservices.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ays.microservices.dtos.ProductRequest;
import com.ays.microservices.dtos.ProductSearchCriteria;
import com.ays.microservices.model.Category;
import com.ays.microservices.model.Product;
import com.ays.microservices.model.ProductImage;
import com.ays.microservices.repository.CategoryRepository;
import com.ays.microservices.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;  // Problem: Too many dependencies

    public Product createProduct(ProductRequest request) {
        // Problem: No validation
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setSku(generateSku(request.getName()));  // Problem: Business logic in service
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        // Problem: Processing images in same transaction
        List<ProductImage> images = request.getImageUrls().stream()
                .map(url -> {
                    ProductImage image = new ProductImage();
                    image.setUrl(url);
                    image.setProduct(product);
                    return image;
                })
                .collect(Collectors.toList());
        product.setImages(images);

        return productRepository.save(product);
    }

    private String generateSku(String name) {
        return UUID.fromString(name).toString();
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Problem: No partial updates
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        return productRepository.save(product);
    }

    public void updateStock(Long productId, int quantity) {
        // Problem: No concurrency handling
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
    }

    // Problem: Complex search in service
    public List<Product> searchProducts(ProductSearchCriteria criteria) {
        if (criteria.getCategoryId() != null) {
            return productRepository.findByCategoryIdAndActiveTrueAndStockQuantityGreaterThan(
                    criteria.getCategoryId(), 0);
        } else if (criteria.getPriceRange() != null) {
            return productRepository.findByPriceBetweenAndActiveTrue(
                    criteria.getPriceRange().getMin(),
                    criteria.getPriceRange().getMax());
        }
        return productRepository.findByActiveTrue();
    }
}