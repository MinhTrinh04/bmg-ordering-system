package com.banhmygac.ordering_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    @Indexed(unique = true) // Thêm mới slug để sau này fetch theo Path Variable cho đẹp
    private String slug;
    @Indexed
    private String categoryId;
    private String name;        // Map vs 'title'
    private BigDecimal price;
    private String currency;
    private String description; // Map vs 'text'
    private String imageUrl;    // Map vs 'image'

}