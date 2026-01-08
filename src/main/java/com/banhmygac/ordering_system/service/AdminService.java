package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.exception.ResourceNotFoundException;
import com.banhmygac.ordering_system.model.DiningSession;
import com.banhmygac.ordering_system.model.OrderStatus;
import com.banhmygac.ordering_system.model.SessionStatus;
import com.banhmygac.ordering_system.repository.DiningSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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

        // Bắn socket báo bàn này đã trống (Clear màn hình khách)
        messagingTemplate.convertAndSend(brokerPrefix + "/table/" + tableNumber, session);

        // Bắn socket báo cho Admin/Bếp biết bàn này đã đóng (để xóa khỏi list)
        messagingTemplate.convertAndSend(brokerPrefix + "/admin/sessions", session);
    }

    // 3. Bếp cập nhật trạng thái Batch (Đã nâng cấp)
    public void updateBatchStatus(String batchId, OrderStatus newStatus) {
        // Tìm đúng Session chứa batchId này
        Query query = new Query(Criteria.where("batches.id").is(batchId));

        // Update trạng thái status của phần tử mảng tìm được (batches.$)
        Update update = new Update()
                .set("batches.$.status", newStatus)
                .set("updatedAt", Instant.now());

        // Thực hiện update và trả về object sau khi update (returnNew = true)
        DiningSession updatedSession = mongoTemplate.findAndModify(
                query,
                update,
                new FindAndModifyOptions().returnNew(true),
                DiningSession.class
        );

        if (updatedSession != null) {
            // QUAN TRỌNG: Bắn tin hiệu Real-time

            // 1. Báo cho Khách hàng (để điện thoại khách hiện: "Đang nấu" -> "Đã xong")
            messagingTemplate.convertAndSend(brokerPrefix + "/table/" + updatedSession.getTableNumber(), updatedSession);

            // 2. Báo cập nhật cho Dashboard quản lý (nếu có nhiều màn hình bếp cùng xem)
            messagingTemplate.convertAndSend(brokerPrefix + "/admin/update", updatedSession);
        } else {
            throw new ResourceNotFoundException("Order Batch not found with id: " + batchId);
        }
    }
}