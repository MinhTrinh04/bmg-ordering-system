package com.banhmygac.ordering_system.model;

public enum SessionStatus {
    OPEN,       // Khách đang ăn, có thể gọi thêm
    PAYING,     // Khách yêu cầu thanh toán (chặn gọi món)
    CLOSED      // Đã thanh toán xong, bàn trống
}