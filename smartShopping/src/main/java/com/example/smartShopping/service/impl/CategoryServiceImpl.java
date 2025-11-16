package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.repository.CategoryRepository;
import com.example.smartShopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse<Category> createCategory(CategoryRequest request) {
        if (categoryRepository.findByName(request.getName()).isPresent()) {
            return ApiResponse.<Category>builder()
                    .success(false)
                    .code(409)
                    .message("Category already exists")
                    .data(null)
                    .build();
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();

        categoryRepository.save(category);

        return ApiResponse.<Category>builder()
                .success(true)
                .code(201)
                .message("Category created successfully")
                .data(category)
                .build();
    }
}
