package com.banhmygac.ordering_system.mapper;

import com.banhmygac.ordering_system.dto.ProductRequest;
import com.banhmygac.ordering_system.dto.ProductResponse;
import com.banhmygac.ordering_system.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // Entity -> DTO
    ProductResponse toResponse(Product product);

    // DTO -> Entity (Create)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true) // Slug sẽ tự gen
    Product toEntity(ProductRequest request);

    // DTO -> Entity (Update)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateEntityFromRequest(@MappingTarget Product product, ProductRequest request);
}