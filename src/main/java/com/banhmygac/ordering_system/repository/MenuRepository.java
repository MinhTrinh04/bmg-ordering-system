package com.banhmygac.ordering_system.repository;

import com.banhmygac.ordering_system.model.MenuCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends MongoRepository<MenuCategory, String> {
    Optional<MenuCategory> findBySlug(String slug);
}