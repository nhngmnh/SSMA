package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.repository.CategoryRepository;
import com.example.smartShopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryCreateResponse createCategory(CategoryRequest request) {
        try {
            Category category = Category.builder()
                    .name(request.getName())
                    .build();

            categoryRepository.save(category);

            CategoryCreateResponse.CategoryDto dto = CategoryCreateResponse.CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .createdAt(category.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .updatedAt(category.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();

            return CategoryCreateResponse.builder()
                    .resultCode("00134")
                    .resultMessage(CategoryCreateResponse.ResultMessage.builder()
                            .en("Category created successfully")
                            .vn("Tạo category thành công")
                            .build())
                    .newCategory(dto)
                    .build();

        } catch (RuntimeException e) {
            return CategoryCreateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(CategoryCreateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public CategoryResponse getAllCategories() {
        try {
            List<CategoryResponse.CategoryDto> categoryDtos = categoryRepository.findAll()
                    .stream()
                    .map(c -> CategoryResponse.CategoryDto.builder()
                            .id(c.getId())
                            .name(c.getName())
                            .createdAt(c.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .updatedAt(c.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build())
                    .collect(Collectors.toList());

            return CategoryResponse.builder()
                    .resultCode("00135")
                    .resultMessage(CategoryResponse.ResultMessage.builder()
                            .en("Get categories successfully")
                            .vn("Lấy danh sách category thành công")
                            .build())
                    .categories(categoryDtos)
                    .build();

        } catch (RuntimeException e) {
            return CategoryResponse.builder()
                    .resultCode("1999")
                    .resultMessage(CategoryResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public CategoryUpdateResponse updateCategoryName(String oldName, String newName) {
        try {
            Category category = categoryRepository.findByName(oldName)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            category.setName(newName);
            categoryRepository.save(category);

            return CategoryUpdateResponse.builder()
                    .resultCode("00141")
                    .resultMessage(CategoryUpdateResponse.ResultMessage.builder()
                            .en("Category updated successfully")
                            .vn("Sửa đổi category thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return CategoryUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(CategoryUpdateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public CategoryDeleteResponse deleteCategoryByName(String name) {
        try {
            Category category = categoryRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            categoryRepository.delete(category);

            return CategoryDeleteResponse.builder()
                    .resultCode("00146")
                    .resultMessage(CategoryDeleteResponse.ResultMessage.builder()
                            .en("Category deleted successfully")
                            .vn("Xóa category thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return CategoryDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(CategoryDeleteResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public CategoryResponse getAllCategoriesFood() {
        try {
            List<CategoryResponse.CategoryDto> categoryDtos = categoryRepository.findAll()
                    .stream()
                    .map(c -> CategoryResponse.CategoryDto.builder()
                            .id(c.getId())
                            .name(c.getName())
                            .createdAt(c.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .updatedAt(c.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build())
                    .collect(Collectors.toList());

            return CategoryResponse.builder()
                    .resultCode("00129")
                    .resultMessage(CategoryResponse.ResultMessage.builder()
                            .en("Successfully retrieved categories")
                            .vn("Lấy các category thành công")
                            .build())
                    .categories(categoryDtos)
                    .build();

        } catch (RuntimeException e) {
            return CategoryResponse.builder()
                    .resultCode("1999")
                    .resultMessage(CategoryResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
}
