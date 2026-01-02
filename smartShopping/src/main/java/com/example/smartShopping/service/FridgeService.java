package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.FridgeCreateResponse;
import com.example.smartShopping.dto.response.FridgeUpdateResponse;
import com.example.smartShopping.dto.response.FridgeDeleteResponse;
import com.example.smartShopping.dto.response.FridgeGetAllResponse;

public interface FridgeService {

    FridgeCreateResponse createFridge(CreateFridgeRequest request);
    FridgeUpdateResponse updateFridge(UpdateFridgeRequest request);
    FridgeDeleteResponse deleteFridgeItem(String foodName);
    FridgeGetAllResponse getAllFridgeItems();
    FridgeGetAllResponse getFridgeItemsByFoodName(String foodName);

}
