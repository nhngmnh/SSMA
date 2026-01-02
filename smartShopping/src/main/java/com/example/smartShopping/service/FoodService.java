package com.example.smartShopping.service;


import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.request.UpdateFoodRequest;
import com.example.smartShopping.dto.response.FoodDeleteResponse;
import com.example.smartShopping.dto.response.FoodResponse;
import com.example.smartShopping.dto.response.FoodUpdateResponse;

public interface FoodService {

    FoodResponse createFood(FoodRequest request, Long userId);
    FoodUpdateResponse updateFood(UpdateFoodRequest req, Long userId);
    FoodDeleteResponse deleteFood(String name, Long userId);

    FoodResponse getAllFoods();
    FoodResponse getFoodById(Long id);
    FoodResponse searchFoods(String keyword);
    FoodResponse getFoodsByGroupId(Long groupId);
}
