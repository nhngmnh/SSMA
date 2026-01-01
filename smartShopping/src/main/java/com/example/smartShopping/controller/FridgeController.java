package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.dto.response.FridgeResponse;
import com.example.smartShopping.service.FridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fridge")
@RequiredArgsConstructor

public class FridgeController {

    private final FridgeService fridgeService;

    @PostMapping
    public ApiResponse<FridgeResponse> createFridge(
            @ModelAttribute CreateFridgeRequest request
    ) {
        FridgeResponse response = fridgeService.createFridge(request);

        return ApiResponse.<FridgeResponse>builder()
                .success(true)
                .code(200)
                .message("Fridge item created successfully.")
                .data(response)
                .build();
    }
    @PutMapping
    public ApiResponse<FridgeResponse> updateFridge(
            @ModelAttribute UpdateFridgeRequest request
    ) {
        FridgeResponse response = fridgeService.updateFridge(request);

        return ApiResponse.<FridgeResponse>builder()
                .success(true)
                .code(200)
                .message("Fridge item updated successfully.")
                .data(response)
                .build();
    }
    @DeleteMapping
    public ApiResponse<Void> deleteFridgeItem(@RequestParam String foodName) {

        fridgeService.deleteFridgeItem(foodName);

        return ApiResponse.<Void>builder()
                .success(true)
                .code(200)
                .message("Fridge item deletion successful.")
                .data(null)
                .build();
    }
    @GetMapping
    public ApiResponse<List<FridgeResponse>> getAllFridgeItems() {
        List<FridgeResponse> fridgeItems = fridgeService.getAllFridgeItems();

        return ApiResponse.<List<FridgeResponse>>builder()
                .success(true)
                .code(200)
                .message("Successfull retrieve all fridge items")
                .data(fridgeItems)
                .build();
    }
    @GetMapping("/{foodName}")
    public ApiResponse<List<FridgeResponse>> getFridgeItem(@PathVariable String foodName) {
        List<FridgeResponse> response = fridgeService.getFridgeItemsByFoodName(foodName);
        return ApiResponse.<List<FridgeResponse>>builder()
                .success(true)
                .code(200)
                .message("Get specific item(s) successfull")
                .data(response)
                .build();
    }

}
