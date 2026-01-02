package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.MenuImportDTO;
import com.banhmygac.ordering_system.dto.MenuResponse;
import com.banhmygac.ordering_system.model.Category;
import com.banhmygac.ordering_system.model.Product;
import com.banhmygac.ordering_system.repository.CategoryRepository;
import com.banhmygac.ordering_system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // Chỉ phục vụ việc import data (mock)
    @Transactional
    public String importMenu(MenuImportDTO importData) {
        for (MenuImportDTO.CategoryDTO catDto : importData.getCategories()) {
            // 1. Lưu Category
            Category category = Category.builder()
                    .name(catDto.getName())
                    .slug(catDto.getSlug())
                    .description(catDto.getDescription())
                    .build();
            Category savedCat = categoryRepository.save(category);

            // 2. Map Items và gán Category ID
            List<Product> products = catDto.getItems().stream().map(itemDto ->
                    Product.builder()
                            .name(itemDto.getName())
                            .price(itemDto.getPrice())
                            .currency(itemDto.getCurrency())
                            .description(itemDto.getDescription())
                            .imageUrl(itemDto.getImageUrl())
                            .categoryId(savedCat.getId()) // LINK PRODUCT VỚI CATEGORY Ở ĐÂY
                            .build()
            ).collect(Collectors.toList());

            // 3. Lưu danh sách Products
            productRepository.saveAll(products);
        }

        return "Imported successfully with " + importData.getCategories().size() + " categories.";
    }

    public List<MenuResponse> getFullMenu() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(cat -> {
            List<Product> products = productRepository.findByCategoryId(cat.getId());

            return MenuResponse.builder()
                    .id(cat.getId())
                    .name(cat.getName())
                    .slug(cat.getSlug())
                    .description(cat.getDescription())
                    .items(products)
                    .build();
        }).collect(Collectors.toList());
    }
}