package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.dto.response.FridgeResponse;
import com.example.smartShopping.service.FridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fridge")
@RequiredArgsConstructor

public class FridgeController {

    private final FridgeService fridgeService;

    @PostMapping
    public Object createFridge(
            @ModelAttribute CreateFridgeRequest request
    ) {
        try {
            return org.springframework.http.ResponseEntity.ok(fridgeService.createFridge(request));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return org.springframework.http.ResponseEntity.status(500).body(errorResponse);
        }
    }
    @PutMapping
    public Object updateFridge(
            @ModelAttribute UpdateFridgeRequest request
    ) {
        try {
            return org.springframework.http.ResponseEntity.ok(fridgeService.updateFridge(request));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return org.springframework.http.ResponseEntity.status(500).body(errorResponse);
        }
    }
    @DeleteMapping
    public Object deleteFridgeItem(@RequestParam String foodName) {
        try {
            fridgeService.deleteFridgeItem(foodName);
            return org.springframework.http.ResponseEntity.ok().build();
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return org.springframework.http.ResponseEntity.status(500).body(errorResponse);
        }
    }
    @GetMapping
    public Object getAllFridgeItems() {
        try {
            return org.springframework.http.ResponseEntity.ok(fridgeService.getAllFridgeItems());
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return org.springframework.http.ResponseEntity.status(500).body(errorResponse);
        }
    }
    @GetMapping("/{foodName}")
    public Object getFridgeItem(@PathVariable String foodName) {
        try {
            return org.springframework.http.ResponseEntity.ok(fridgeService.getFridgeItemsByFoodName(foodName));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return org.springframework.http.ResponseEntity.status(500).body(errorResponse);
        }
    }

}
