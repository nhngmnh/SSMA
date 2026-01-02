package com.example.smartShopping.controller;



import com.example.smartShopping.dto.request.CategoryRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.dto.response.CategoryCreateResponse;
import com.example.smartShopping.dto.response.CategoryUpdateResponse;
import com.example.smartShopping.dto.response.CategoryDeleteResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.CategoryService;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.configuration.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public Object createCategory(@RequestParam("name") String name, @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            CategoryRequest request = new CategoryRequest();
            request.setName(name);
            return ResponseEntity.ok(categoryService.createCategory(request));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


    @GetMapping
    public Object getAllCategories(@RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


    @PutMapping
    public Object updateCategory(
            @RequestParam String oldName,
            @RequestParam String newName,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            return ResponseEntity.ok(categoryService.updateCategoryName(oldName, newName));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }



    @DeleteMapping
    public Object deleteCategory(@RequestParam String name, @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            return ResponseEntity.ok(categoryService.deleteCategoryByName(name));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    private boolean isAdmin(String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            User user = userRepository.findByEmail(email).orElse(null);
            return user != null && user.getIsAdmin() != null && user.getIsAdmin();
        } catch (Exception e) {
            return false;
        }
    }
}
