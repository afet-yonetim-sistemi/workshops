package com.ays.microservices.controller;

import java.util.List;
import java.util.Map;

import com.ays.microservices.model.Category;
import com.ays.microservices.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseTestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/test-db")
    public List<Map<String, Object>> testDatabase() {
        return jdbcTemplate.queryForList("SELECT * FROM customers");
    }

    @GetMapping("/debug-categories")
    public List<Category> debugCategories() {
        return categoryRepository.findAll();
    }
}