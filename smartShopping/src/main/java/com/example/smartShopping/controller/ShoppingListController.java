package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.CreateShoppingListRequest;
import com.example.smartShopping.dto.request.CreateShoppingTasksRequest;
import com.example.smartShopping.dto.request.UpdateShoppingListRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.dto.response.ResultMessage;
import com.example.smartShopping.dto.response.ShoppingListResponse;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.ShoppingListService;
import com.example.smartShopping.service.ShoppingTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shopping")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ShoppingTaskService shoppingTaskService;
    @PostMapping
    public ResponseEntity<?> create(
            @ModelAttribute CreateShoppingListRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();
        System.out.println("USERNAME = " + username);

        ShoppingListResponse response =
                shoppingListService.create(request, username);


        return ResponseEntity.ok(
                ApiResponse.<ShoppingListResponse>builder()
                        .success(true)
                        .message("Danh sách mua sắm đã được tạo thành công")
                        .code(249)
                        .data(response)
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<?> update(
            @ModelAttribute UpdateShoppingListRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        ShoppingListResponse response =
                shoppingListService.update(request, currentUser);

        return ResponseEntity.ok(
                ApiResponse.<ShoppingListResponse>builder()
                        .success(true)
                        .message("Shopping list updated successfully | Cập nhật danh sách mua sắm thành công")
                        .code(249)
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam Long listId,
            @AuthenticationPrincipal User currentUser
    ) {
        shoppingListService.delete(listId, currentUser);


        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa danh sách mua sắm thành công")
                        .code(275)
                        .build()
        );
    }
    @PostMapping("/task")
    public ResponseEntity<?> createTasks(
            @RequestBody CreateShoppingTasksRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();

        shoppingTaskService.createTasks(request, username);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Thêm nhiệm vụ thành công")
                        .code(287)
                        .build()
        );
    }


}
