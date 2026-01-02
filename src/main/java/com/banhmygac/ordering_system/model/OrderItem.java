package com.banhmygac.ordering_system.model;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}