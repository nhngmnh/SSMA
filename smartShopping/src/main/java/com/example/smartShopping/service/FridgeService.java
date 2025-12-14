package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.FridgeResponse;

import java.util.List;

public interface FridgeService {

    FridgeResponse createFridge(CreateFridgeRequest request);
    FridgeResponse updateFridge(UpdateFridgeRequest request);
    void deleteFridgeItem(String foodName);
    // Lấy tất cả fridge items của user
    List<FridgeResponse> getAllFridgeItems();
    List<FridgeResponse> getFridgeItemsByFoodName(String foodName);

}
