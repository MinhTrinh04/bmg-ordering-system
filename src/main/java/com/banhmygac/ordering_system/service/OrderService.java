package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.OrderRequest;
import com.banhmygac.ordering_system.exception.RateLimitExceededException;
import com.banhmygac.ordering_system.model.*;
import com.banhmygac.ordering_system.repository.DiningSessionRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final DiningSessionRepository sessionRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${app.rate-limit.capacity}")
    private long capacity;
    @Value("${app.rate-limit.refill-tokens}")
    private long refillTokens;
    @Value("${app.rate-limit.refill-duration}")
    private long refillDuration;
    @Value("${app.websocket.broker}")
    private String brokerPrefix;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket resolveBucket(String tableNumber) {
        return cache.computeIfAbsent(tableNumber, this::newBucket);
    }

    private Bucket newBucket(String key) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(capacity, Refill.intervally(refillTokens, Duration.ofSeconds(refillDuration))))
                .build();
    }

    @Transactional
    public DiningSession createOrder(OrderRequest request) {
        if (!resolveBucket(request.getTableNumber()).tryConsume(1)) {
            throw new RateLimitExceededException("Too many requests from table " + request.getTableNumber() +
                    ". Please wait " + refillDuration + " seconds.");
        }

        String tableNumber = request.getTableNumber();

        DiningSession session = sessionRepository.findByTableNumberAndStatus(tableNumber, SessionStatus.OPEN)
                .orElseGet(() -> createNewSession(tableNumber));

        OrderBatch newBatch = OrderBatch.builder()
                .id(UUID.randomUUID().toString())
                .items(request.getItems())
                .orderedAt(Instant.now())
                .status(OrderStatus.PENDING)
                .build();

        session.getBatches().add(newBatch);
        recalculateTotal(session);

        DiningSession savedSession = sessionRepository.save(session);

        messagingTemplate.convertAndSend(brokerPrefix + "/kitchen", newBatch);

        messagingTemplate.convertAndSend(brokerPrefix + "/table/" + tableNumber, savedSession);

        return savedSession;
    }

    private DiningSession createNewSession(String tableNumber) {
        return DiningSession.builder()
                .tableNumber(tableNumber)
                .status(SessionStatus.OPEN)
                .batches(new ArrayList<>())
                .totalAmount(BigDecimal.ZERO)
                .createdAt(Instant.now())
                .build();
    }

    private void recalculateTotal(DiningSession session) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderBatch batch : session.getBatches()) {
            if (batch.getStatus() != OrderStatus.CANCELLED) {
                BigDecimal batchTotal = batch.getItems().stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                total = total.add(batchTotal);
            }
        }
        session.setTotalAmount(total);
    }

    public DiningSession getSessionByTable(String tableNumber) {
        return sessionRepository.findByTableNumberAndStatus(tableNumber, SessionStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("Table " + tableNumber + " is currently empty"));
    }
}