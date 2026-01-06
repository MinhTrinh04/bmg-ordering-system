package com.banhmygac.ordering_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

// Chỉ để phục vụ việc import data (mock)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuImportDTO {
    private List<CategoryDTO> categories;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryDTO {
        private String name;
        private String slug;
        private String description;
        private List<ItemDTO> items;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItemDTO {
        @JsonProperty("title") // Map trường 'title' trong JSON vào biến 'name'
        private String name;

        private BigDecimal price;
        private String currency;

        @JsonProperty("text")  // Map trường 'text' trong JSON vào biến 'description'
        private String description;

        @JsonProperty("image") // Map trường 'image' trong JSON vào biến 'imageUrl'
        private String imageUrl;

        // Không khai báo rating -> Jackson sẽ tự bỏ qua
    }
}