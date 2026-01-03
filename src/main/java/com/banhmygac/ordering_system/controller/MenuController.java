package com.banhmygac.ordering_system.controller;

import com.banhmygac.ordering_system.dto.CategoryResponse;
import com.banhmygac.ordering_system.dto.MenuImportDTO;
import com.banhmygac.ordering_system.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    // Chỉ để mock data
    @PostMapping("/import")
    public ResponseEntity<String> importMenu(@RequestBody MenuImportDTO importData) {
        return ResponseEntity.ok(menuService.importMenu(importData));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getMenu() {
        return ResponseEntity.ok(menuService.getFullMenu());
    }
}