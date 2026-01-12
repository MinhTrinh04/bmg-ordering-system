package com.banhmygac.ordering_system.dto;

import com.banhmygac.ordering_system.model.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    @NotBlank(message = "Table number is required")
    private String tableNumber;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItem> items;
}