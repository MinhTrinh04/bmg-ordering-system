package com.banhmygac.ordering_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu_categories") // Tên bảng trong Mongo
public class MenuCategory {
    @Id
    private String id;
    private String name;        // Ví dụ: "Tất cả món ăn"
    private String slug;        // Ví dụ: "tat-ca-mon-an"
    private String description;
    private List<MenuItem> items;
}