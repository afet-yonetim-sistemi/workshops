package com.ays.microservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRange {
    private Double min;
    private Double max;
}