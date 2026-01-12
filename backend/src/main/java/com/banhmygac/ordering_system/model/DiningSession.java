package com.banhmygac.ordering_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dining_sessions")
public class DiningSession {
    @Id
    private String id;

    @Indexed // Index để tìm nhanh session theo số bàn
    private String tableNumber;

    private SessionStatus status;

    // Danh sách các lượt gọi món (Thay thế cho danh sách items phẳng trước đây)
    @Builder.Default
    private List<OrderBatch> batches = new ArrayList<>();

    private BigDecimal totalAmount;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}