package com.banhmygac.ordering_system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBatch {
    private String id;              // ID riêng của đợt gọi này (để hủy/bếp track)
    private List<OrderItem> items;  // Danh sách món trong đợt này
    private Instant orderedAt;      // Thời gian gọi

    // Trạng thái đợt này: PENDING (Chờ), CONFIRMED (Bếp nhận), SERVED (Đã ra), CANCELLED (Hủy)
    private OrderStatus status;
}