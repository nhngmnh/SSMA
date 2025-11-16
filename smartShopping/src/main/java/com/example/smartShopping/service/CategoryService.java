package com.example.smartShopping.service;


import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.dto.response.ApiResponse;

public interface CategoryService {
    ApiResponse createCategory(CategoryRequest request);
}
