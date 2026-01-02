package com.banhmygac.ordering_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private String id;
    private String name;        // Map vs 'title'
    private BigDecimal price;
    private String currency;
    private String description; // Map vs 'text'
    private String imageUrl;    // Map vs 'image'
}