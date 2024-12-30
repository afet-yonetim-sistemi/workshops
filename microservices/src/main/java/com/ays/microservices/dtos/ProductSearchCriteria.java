package com.ays.microservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductSearchCriteria {
    private Long categoryId;
    private PriceRange priceRange;
    private String keyword;
    private Boolean inStock;
}