package com.banhmygac.ordering_system.controller;

import com.banhmygac.ordering_system.dto.OrderRequest;
import com.banhmygac.ordering_system.model.DiningSession;
import com.banhmygac.ordering_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<DiningSession> placeOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{tableNumber}")
    public ResponseEntity<DiningSession> getSession(@PathVariable String tableNumber) {
        try {
            return ResponseEntity.ok(orderService.getSessionByTable(tableNumber));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}