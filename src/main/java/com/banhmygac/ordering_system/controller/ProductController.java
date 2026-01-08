package com.banhmygac.ordering_system.controller;

import com.banhmygac.ordering_system.dto.ProductRequest;
import com.banhmygac.ordering_system.dto.ProductResponse;
import com.banhmygac.ordering_system.model.Product;
import com.banhmygac.ordering_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductResponse> getProductBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.getProductBySlug(slug));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String slug, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(slug, request));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String slug) {
        productService.deleteProduct(slug);
        return ResponseEntity.noContent().build();
    }
}