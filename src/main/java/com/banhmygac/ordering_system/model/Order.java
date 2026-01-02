package com.banhmygac.ordering_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.List;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String tableNumber; // Số bàn từ QR Code
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private OrderStatus status; // PENDING, CONFIRMED, COMPLETED, CANCELLED

    @CreatedDate
    private Instant createdAt;
}