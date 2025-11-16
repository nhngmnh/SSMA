package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.repository.CategoryRepository;
import com.example.smartShopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return ApiResponse.<List<Category>>builder()
                .success(true)
                .code(200)
                .message("Get categories successfully")
                .data(categories)
                .build();
    }
    @Override
    public ApiResponse updateCategoryName(String oldName, String newName) {
        Category category = categoryRepository.findByName(oldName)
                .orElse(null);

        if (category == null) {
            return ApiResponse.builder()
                    .success(false)
                    .code(404)
                    .message("Category not found")
                    .data(null)
                    .build();
        }

        // Kiểm tra trùng newName
        if (categoryRepository.findByName(newName).isPresent()) {
            return ApiResponse.builder()
                    .success(false)
                    .code(409)
                    .message("Category name already exists")
                    .data(null)
                    .build();
        }

        category.setName(newName);
        categoryRepository.save(category);

        return ApiResponse.builder()
                .success(true)
                .code(141)
                .message("Sửa đổi category thành công")
                .data(null)
                .build();
    }
    @Override
    public ApiResponse deleteCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElse(null);

        if (category == null) {
            return ApiResponse.builder()
                    .success(false)
                    .code(404)
                    .message("Category not found")
                    .data(null)
                    .build();
        }

        categoryRepository.delete(category);

        return ApiResponse.builder()
                .success(true)
                .code(146)
                .message("Xóa category thành công")
                .data(null)
                .build();
    }
}
