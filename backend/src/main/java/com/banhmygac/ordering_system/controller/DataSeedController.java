package com.banhmygac.ordering_system.controller;

import com.banhmygac.ordering_system.dto.MenuImportDTO;
import com.banhmygac.ordering_system.service.DataSeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class DataSeedController {

    private final DataSeedService dataSeedService;
    @PostMapping("/import")
    public ResponseEntity<String> importMenu(@RequestBody MenuImportDTO importData) {
        return ResponseEntity.ok(dataSeedService.importMenu(importData));
    }
}