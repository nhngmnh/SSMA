package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.FridgeCreateResponse;
import com.example.smartShopping.dto.response.FridgeUpdateResponse;
import com.example.smartShopping.dto.response.FridgeDeleteResponse;
import com.example.smartShopping.dto.response.FridgeGetAllResponse;

public interface FridgeService {

    FridgeCreateResponse createFridge(CreateFridgeRequest request, Long userId, String authHeader);
    FridgeUpdateResponse updateFridge(UpdateFridgeRequest request, String authHeader);
    FridgeDeleteResponse deleteFridgeItem(String foodName, String authHeader);
    FridgeGetAllResponse getAllFridgeItems(Long userId, String authHeader);
    FridgeGetAllResponse getFridgeItemsByFoodName(String foodName, Long userId, String authHeader);

}
