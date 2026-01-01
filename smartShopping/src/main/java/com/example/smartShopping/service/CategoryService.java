package com.example.smartShopping.service;


import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.CategoryResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.dto.response.ApiResponse;

import java.util.List;

public interface CategoryService {
    ApiResponse createCategory(CategoryRequest request);
    ApiResponse<List<Category>> getAllCategories();
    ApiResponse updateCategoryName(String oldName, String newName);
    ApiResponse deleteCategoryByName(String name);

    CategoryResponse getAllCategoriesFood();
}
