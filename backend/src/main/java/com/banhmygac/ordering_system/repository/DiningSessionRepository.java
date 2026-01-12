package com.banhmygac.ordering_system.repository;

import com.banhmygac.ordering_system.model.DiningSession;
import com.banhmygac.ordering_system.model.SessionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface DiningSessionRepository extends MongoRepository<DiningSession, String> {
    Optional<DiningSession> findByTableNumberAndStatus(String tableNumber, SessionStatus status);
}