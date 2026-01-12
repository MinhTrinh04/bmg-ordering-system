package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.exception.ResourceNotFoundException;
import com.banhmygac.ordering_system.model.DiningSession;
import com.banhmygac.ordering_system.model.OrderBatch;
import com.banhmygac.ordering_system.model.OrderStatus;
import com.banhmygac.ordering_system.model.SessionStatus;
import com.banhmygac.ordering_system.repository.DiningSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DiningSessionRepository sessionRepository;
    private final MongoTemplate mongoTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${app.websocket.broker}")
    private String brokerPrefix;

    public List<DiningSession> getAllActiveSessions() {
        return sessionRepository.findAll().stream()
                .filter(s -> s.getStatus() != SessionStatus.CLOSED)
                .toList();
    }

    public void closeSession(String tableNumber) {
        DiningSession session = sessionRepository.findByTableNumberAndStatus(tableNumber, SessionStatus.PAYING)
                .or(() -> sessionRepository.findByTableNumberAndStatus(tableNumber, SessionStatus.OPEN))
                .orElseThrow(() -> new ResourceNotFoundException("No active session for table " + tableNumber));

        session.setStatus(SessionStatus.CLOSED);
        session.setUpdatedAt(Instant.now());
        sessionRepository.save(session);

        messagingTemplate.convertAndSend(brokerPrefix + "/table/" + tableNumber, session);
        messagingTemplate.convertAndSend(brokerPrefix + "/admin/sessions", session);
    }

    @Transactional
    public void updateBatchStatus(String batchId, OrderStatus newStatus) {
        Query query = new Query(Criteria.where("batches.id").is(batchId));
        DiningSession session = mongoTemplate.findOne(query, DiningSession.class);

        if (session == null) {
            throw new ResourceNotFoundException("Session not found for batch id: " + batchId);
        }

        boolean isUpdated = false;
        BigDecimal newTotal = BigDecimal.ZERO;

        for (OrderBatch batch : session.getBatches()) {
            if (batch.getId().equals(batchId)) {
                batch.setStatus(newStatus);
                isUpdated = true;
            }

            if (batch.getStatus() != OrderStatus.CANCELLED) {
                BigDecimal batchTotal = batch.getItems().stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                newTotal = newTotal.add(batchTotal);
            }
        }

        if (!isUpdated) {
            throw new ResourceNotFoundException("Batch ID found in query but missing in list logic");
        }

        session.setTotalAmount(newTotal);
        session.setUpdatedAt(Instant.now());

        sessionRepository.save(session);

        messagingTemplate.convertAndSend(brokerPrefix + "/table/" + session.getTableNumber(), session);
        messagingTemplate.convertAndSend(brokerPrefix + "/admin/update", session);
    }
}