package com.banhmygac.ordering_system.repository;

import com.banhmygac.ordering_system.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategoryId(String categoryId);
    Optional<Product> findBySlug(String slug);
    boolean existsBySlug(String slug);
}