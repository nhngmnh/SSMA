package com.example.smartShopping.controller;



import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        ApiResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @PutMapping
    public ResponseEntity<ApiResponse> updateCategory(
            @RequestParam String oldName,
            @RequestParam String newName) {

        ApiResponse response = categoryService.updateCategoryName(oldName, newName);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam String name) {
        ApiResponse response = categoryService.deleteCategoryByName(name);
        return ResponseEntity.ok(response);
    }
}
