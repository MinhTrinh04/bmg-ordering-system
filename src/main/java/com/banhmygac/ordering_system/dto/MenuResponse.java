package com.banhmygac.ordering_system.dto;

import com.banhmygac.ordering_system.model.Product;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MenuResponse {
    private String id;
    private String name;
    private String slug;
    private String description;
    private List<Product> items;
}