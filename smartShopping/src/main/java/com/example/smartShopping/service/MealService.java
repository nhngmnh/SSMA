package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.DeleteMealRequest;
import com.example.smartShopping.dto.request.MealRequest;
import com.example.smartShopping.dto.request.UpdateMealRequest;
import com.example.smartShopping.dto.response.MealDeleteResponse;
import com.example.smartShopping.dto.response.MealDetailResponse;
import com.example.smartShopping.dto.response.MealGetAllResponse;
import com.example.smartShopping.dto.response.MealResponse;
import com.example.smartShopping.dto.response.MealUpdateResponse;

public interface MealService {
    MealResponse createMeal(MealRequest request, Long userId, String authHeader);
    MealUpdateResponse updateMeal(UpdateMealRequest request);
    MealDeleteResponse deleteMeal(DeleteMealRequest request);
    MealGetAllResponse getAllMeals();
    MealDetailResponse getMealById(Long id);
}
