package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.OrderRequest;
import com.banhmygac.ordering_system.mapper.OrderMapper;
import com.banhmygac.ordering_system.model.Order;
import com.banhmygac.ordering_system.model.OrderItem;
import com.banhmygac.ordering_system.repository.OrderRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final SimpMessagingTemplate messagingTemplate;

    // Simple in-memory rate limiting: 1 order per 10 seconds per table
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket resolveBucket(String tableNumber) {
        return cache.computeIfAbsent(tableNumber, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(1, Refill.intervally(1, Duration.ofSeconds(10))))
                .build();
    }

    public Order createOrder(OrderRequest request) {
        // 1. Rate Limiting Check
        if (!resolveBucket(request.getTableNumber()).tryConsume(1)) {
            throw new RuntimeException("Too many requests - Please wait a moment");
        }

        // 2. Map & Calculate Total
        Order order = orderMapper.toEntity(request);
        BigDecimal total = request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);

        // 3. Save to MongoDB
        Order savedOrder = orderRepository.save(order);

        // 4. Notify Kitchen (Real-time)
        messagingTemplate.convertAndSend("/topic/orders", savedOrder);

        return savedOrder;
    }
}