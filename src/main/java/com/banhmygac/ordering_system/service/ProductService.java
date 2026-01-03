package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.ProductRequest;
import com.banhmygac.ordering_system.exception.ResourceNotFoundException;
import com.banhmygac.ordering_system.model.Product;
import com.banhmygac.ordering_system.repository.CategoryRepository;
import com.banhmygac.ordering_system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(ProductRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + request.getCategoryId());
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .categoryId(request.getCategoryId())
                .build();

        return productRepository.save(product);
    }


    public Product updateProduct(String id, ProductRequest request) {
        Product existingProduct = getProductById(id);

        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + request.getCategoryId());
        }

        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setCurrency(request.getCurrency());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setImageUrl(request.getImageUrl());
        existingProduct.setCategoryId(request.getCategoryId());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}