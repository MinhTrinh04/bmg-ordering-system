package com.banhmygac.ordering_system.controller;

import com.banhmygac.ordering_system.model.DiningSession;
import com.banhmygac.ordering_system.model.OrderStatus;
import com.banhmygac.ordering_system.model.SessionStatus;
import com.banhmygac.ordering_system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/sessions")
    public ResponseEntity<List<DiningSession>> getAllActiveSessions() {
        return ResponseEntity.ok(adminService.getAllActiveSessions());
    }

    @PostMapping("/sessions/{tableNumber}/close")
    public ResponseEntity<Void> closeSession(@PathVariable String tableNumber) {
        adminService.closeSession(tableNumber);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/batches/{batchId}/status")
    public ResponseEntity<Void> updateBatchStatus(
            @PathVariable String batchId,
            @RequestParam OrderStatus status) {
        adminService.updateBatchStatus(batchId, status);
        return ResponseEntity.ok().build();
    }
}