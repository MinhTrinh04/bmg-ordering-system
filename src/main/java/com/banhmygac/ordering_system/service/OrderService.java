package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.OrderRequest;
import com.banhmygac.ordering_system.mapper.OrderMapper;
import com.banhmygac.ordering_system.model.Order;
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
import com.banhmygac.ordering_system.exception.RateLimitExceededException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final SimpMessagingTemplate messagingTemplate;

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
        if (!resolveBucket(request.getTableNumber()).tryConsume(1)) {
            throw new RateLimitExceededException("Too many requests from table " + request.getTableNumber() + ". Please wait 10 seconds.");        }

        Order order = orderMapper.toEntity(request);
        BigDecimal total = request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        messagingTemplate.convertAndSend("/topic/orders", savedOrder);

        return savedOrder;
    }
}