package com.example.smartShopping.service;


import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.*;

public interface CategoryService {
    CategoryCreateResponse createCategory(CategoryRequest request);
    CategoryResponse getAllCategories();
    CategoryUpdateResponse updateCategoryName(String oldName, String newName);
    CategoryDeleteResponse deleteCategoryByName(String name);
    CategoryResponse getAllCategoriesFood();
}
