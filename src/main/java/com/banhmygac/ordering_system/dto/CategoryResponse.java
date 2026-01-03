package com.banhmygac.ordering_system.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String name;
    private String slug;
    private String description;
    private List<ProductResponse> items;
}