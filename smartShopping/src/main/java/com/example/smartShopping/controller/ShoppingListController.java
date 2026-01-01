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

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shopping")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ShoppingTaskService shoppingTaskService;
    @PostMapping
    public Object create(
            @ModelAttribute CreateShoppingListRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            String username = userDetails.getUsername();
            System.out.println("USERNAME = " + username);

            ShoppingListResponse response =
                    shoppingListService.create(request, username);

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

    @PutMapping
    public Object update(
            @ModelAttribute UpdateShoppingListRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        try {
            ShoppingListResponse response =
                    shoppingListService.update(request, currentUser);

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

    @DeleteMapping
    public Object delete(
            @RequestParam Long listId,
            @AuthenticationPrincipal User currentUser
    ) {
        try {
            shoppingListService.delete(listId, currentUser);
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
