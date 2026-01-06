package com.banhmygac.ordering_system.mapper;

import com.banhmygac.ordering_system.dto.OrderRequest;
import com.banhmygac.ordering_system.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "totalAmount", ignore = true) // Sẽ tính toán trong service
    Order toEntity(OrderRequest request);
}