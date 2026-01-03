package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.ProductRequest;
import com.banhmygac.ordering_system.dto.ProductResponse;
import com.banhmygac.ordering_system.exception.ResourceNotFoundException;
import com.banhmygac.ordering_system.mapper.ProductMapper;
import com.banhmygac.ordering_system.model.Product;
import com.banhmygac.ordering_system.repository.CategoryRepository;
import com.banhmygac.ordering_system.repository.ProductRepository;
import com.banhmygac.ordering_system.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with slug: " + slug));
        return productMapper.toResponse(product);
    }

    // CREATE
    public ProductResponse createProduct(ProductRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found");
        }

        Product product = productMapper.toEntity(request);

        // Tạo slug từ tên (nếu trùng thì thêm suffix random hoặc xử lý thêm)
        String slug = SlugUtil.makeSlug(request.getName());
        // Simple check trùng slug (thực tế nên dùng while loop thêm số đếm)
        if (productRepository.existsBySlug(slug)) {
            slug += "-" + System.currentTimeMillis();
        }
        product.setSlug(slug);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(String slug, ProductRequest request) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with slug: " + slug));

        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found");
        }

        // Nếu đổi tên thì đổi luôn slug
        if (!product.getName().equals(request.getName())) {
            String newSlug = SlugUtil.makeSlug(request.getName());
            if (!newSlug.equals(product.getSlug())) {
                if (productRepository.existsBySlug(newSlug)) {
                    newSlug += "-" + System.currentTimeMillis();
                }
                product.setSlug(newSlug);
            }
        }

        productMapper.updateEntityFromRequest(product, request);

        return productMapper.toResponse(productRepository.save(product));
    }

    public void deleteProduct(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}