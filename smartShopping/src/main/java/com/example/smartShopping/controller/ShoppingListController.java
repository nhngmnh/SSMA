package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.*;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.ShoppingListService;
import com.example.smartShopping.service.ShoppingTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shopping-lists")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ShoppingTaskService shoppingTaskService;

    /**
     * POST /api/shopping-lists - Tạo danh sách mới
     */
    @PostMapping
    public Object create(
            @ModelAttribute CreateShoppingListRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            String username = userDetails.getUsername();
            ShoppingListCreateResponse response = shoppingListService.create(request, username);
            return ResponseEntity.ok(response);
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

    /**
     * GET /api/shopping-lists - Lấy danh sách tất cả shopping lists
     */
    @GetMapping
    public Object getAll() {
        try {
            Long userId = getCurrentUserId();
            ShoppingListGetAllResponse response = shoppingListService.getAll(userId);
            return ResponseEntity.ok(response);
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

    /**
     * GET /api/shopping-lists/:id - Lấy chi tiết một shopping list
     */
    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        try {
            Long userId = getCurrentUserId();
            ShoppingListDetailResponse response = shoppingListService.getById(id, userId);
            return ResponseEntity.ok(response);
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

    /**
     * PUT /api/shopping-lists/:id - Cập nhật shopping list
     */
    @PutMapping("/{id}")
    public Object update(
            @PathVariable Long id,
            @ModelAttribute UpdateShoppingListRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        try {
            request.setListId(id);
            ShoppingListUpdateResponse response = shoppingListService.update(request, currentUser);
            return ResponseEntity.ok(response);
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

    /**
     * DELETE /api/shopping-lists/:id - Xóa shopping list
     */
    @DeleteMapping("/{id}")
    public Object delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        try {
            ShoppingListDeleteResponse response = shoppingListService.delete(id, currentUser);
            return ResponseEntity.ok(response);
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

    /**
     * POST /api/shopping-lists/:id/items - Thêm item vào shopping list
     */
    @PostMapping("/{id}/items")
    public Object addItem(
            @PathVariable Long id,
            @RequestBody AddShoppingItemRequest request
    ) {
        try {
            Long userId = getCurrentUserId();
            ShoppingListDetailResponse response = shoppingListService.addItem(id, request, userId);
            return ResponseEntity.ok(response);
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

    /**
     * PUT /api/shopping-lists/:id/items/:itemId - Cập nhật item
     */
    @PutMapping("/{id}/items/{itemId}")
    public Object updateItem(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @RequestBody UpdateShoppingItemRequest request
    ) {
        try {
            Long userId = getCurrentUserId();
            ShoppingListDetailResponse response = shoppingListService.updateItem(id, itemId, request, userId);
            return ResponseEntity.ok(response);
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

    /**
     * DELETE /api/shopping-lists/:id/items/:itemId - Xóa item
     */
    @DeleteMapping("/{id}/items/{itemId}")
    public Object deleteItem(
            @PathVariable Long id,
            @PathVariable Long itemId
    ) {
        try {
            Long userId = getCurrentUserId();
            ShoppingListDetailResponse response = shoppingListService.deleteItem(id, itemId, userId);
            return ResponseEntity.ok(response);
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

    /**
     * POST /api/shopping-lists/:id/complete - Hoàn thành shopping list
     */
    @PostMapping("/{id}/complete")
    public Object completeList(@PathVariable Long id) {
        try {
            Long userId = getCurrentUserId();
            ShoppingListDetailResponse response = shoppingListService.completeList(id, userId);
            return ResponseEntity.ok(response);
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

    // Helper method
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getName());
    }

    // Legacy endpoint - giữ lại nếu cần tương thích ngược
    @PostMapping("/task")
    public Object createTasks(
            @RequestBody CreateShoppingTasksRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            String username = userDetails.getUsername();
            shoppingTaskService.createTasks(request, username);
            return ResponseEntity.ok().build();
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

}
