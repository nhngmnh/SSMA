package com.example.smartShopping.controller;



import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestParam("name") String name) {
        CategoryRequest request = new CategoryRequest();
        request.setName(name);  // gán name từ requestParam
        ApiResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse> updateCategory(
            @RequestParam String oldName,
            @RequestParam String newName) {

        ApiResponse response = categoryService.updateCategoryName(oldName, newName);
        return ResponseEntity.ok(response);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam String name) {
        ApiResponse response = categoryService.deleteCategoryByName(name);
        return ResponseEntity.ok(response);
    }
}
