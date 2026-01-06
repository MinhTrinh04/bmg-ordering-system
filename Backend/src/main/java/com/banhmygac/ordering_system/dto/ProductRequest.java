package com.banhmygac.ordering_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @Min(value = 0, message = "Price must be positive")
    private BigDecimal price;

    private String currency = "VNĐ";

    private String description;

    private String imageUrl;

    @NotBlank(message = "Category ID is required")
    private String categoryId;
}