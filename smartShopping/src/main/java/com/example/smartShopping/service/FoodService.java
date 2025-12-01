package com.example.smartShopping.service;


import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.response.FoodResponse;

public interface FoodService {

    FoodResponse createFood(FoodRequest request, Long userId);
}
