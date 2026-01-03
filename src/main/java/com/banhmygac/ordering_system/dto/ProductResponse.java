package com.banhmygac.ordering_system.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private String id;
    private String slug;
    private String name;
    private BigDecimal price;
    private String currency;
    private String description;
    private String imageUrl;
    private String categoryId;
}