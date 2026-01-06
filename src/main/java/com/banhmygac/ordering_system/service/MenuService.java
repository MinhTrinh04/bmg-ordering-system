package com.banhmygac.ordering_system.service;

import com.banhmygac.ordering_system.dto.CategoryResponse;
import com.banhmygac.ordering_system.dto.MenuImportDTO;
import com.banhmygac.ordering_system.dto.ProductResponse;
import com.banhmygac.ordering_system.mapper.ProductMapper;
import com.banhmygac.ordering_system.model.Category;
import com.banhmygac.ordering_system.model.Product;
import com.banhmygac.ordering_system.repository.CategoryRepository;
import com.banhmygac.ordering_system.repository.ProductRepository;
import com.banhmygac.ordering_system.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public String importMenu(MenuImportDTO importData) {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        int importedCount = 0;

        for (MenuImportDTO.CategoryDTO catDto : importData.getCategories()) {
            // LOGIC QUAN TRỌNG: Bỏ qua category "Tất cả" từ file JSON
            // Vì chúng ta sẽ tự query lấy món ăn theo từng danh mục nhỏ
            if ("tat-ca-mon-an".equalsIgnoreCase(catDto.getSlug()) || "Tất cả món ăn".equalsIgnoreCase(catDto.getName())) {
                continue;
            }

            Category category = Category.builder()
                    .name(catDto.getName())
                    .slug(catDto.getSlug())
                    .description(catDto.getDescription())
                    .build();
            Category savedCat = categoryRepository.save(category);

            List<Product> products = catDto.getItems().stream().map(itemDto ->
                    Product.builder()
                            .name(itemDto.getName())
                            .slug(SlugUtil.makeSlug(itemDto.getName())) // Tự gen slug
                            .price(itemDto.getPrice())
                            .currency(itemDto.getCurrency())
                            .description(itemDto.getDescription())
                            .imageUrl(itemDto.getImageUrl())
                            .categoryId(savedCat.getId())
                            .build()
            ).collect(Collectors.toList());

            productRepository.saveAll(products);
            importedCount++;
        }

        return "Imported successfully " + importedCount + " categories (skipped 'All Products').";
    }

    public List<CategoryResponse> getFullMenu() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(cat -> {
            List<ProductResponse> productDtos = productRepository.findByCategoryId(cat.getId())
                    .stream()
                    .map(productMapper::toResponse)
                    .collect(Collectors.toList());

            return CategoryResponse.builder()
                    .id(cat.getId())
                    .name(cat.getName())
                    .slug(cat.getSlug())
                    .description(cat.getDescription())
                    .items(productDtos)
                    .build();
        }).collect(Collectors.toList());
    }
}