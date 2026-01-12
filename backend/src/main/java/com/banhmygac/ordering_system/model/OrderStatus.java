package com.banhmygac.ordering_system.model;

public enum OrderStatus {
    PENDING,    // 1. Chờ nấu
    IN_PROGRESS,// 2. Đang nấu
    COMPLETED,  // 3. Xong
    CANCELLED   // 4. Hủy
}