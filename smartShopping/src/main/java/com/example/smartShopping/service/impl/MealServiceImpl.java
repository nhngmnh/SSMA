package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.DeleteMealRequest;
import com.example.smartShopping.dto.request.MealRequest;
import com.example.smartShopping.dto.request.UpdateMealRequest;
import com.example.smartShopping.dto.response.MealDeleteResponse;
import com.example.smartShopping.dto.response.MealResponse;
import com.example.smartShopping.dto.response.MealUpdateResponse;
import com.example.smartShopping.entity.Meal;
import com.example.smartShopping.repository.MealRepository;
import com.example.smartShopping.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;

    @Override
    public MealResponse createMeal(MealRequest request, Long userId) {
        try {
            // Create timestamp in ISO format
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Create meal entity
            Meal meal = Meal.builder()
                    .name(request.getName())
                    .timestamp(request.getTimestamp())
                    .status("NOT_PASS_YET")
                    .foodId(request.getFoodId())
                    .userId(userId)
                    .createdAt(timestamp)
                    .updatedAt(timestamp)
                    .build();

            // Save meal
            Meal savedMeal = mealRepository.save(meal);

            // Build response
            MealResponse.NewPlan newPlan = MealResponse.NewPlan.builder()
                    .id(savedMeal.getId())
                    .name(savedMeal.getName())
                    .timestamp(savedMeal.getTimestamp())
                    .status(savedMeal.getStatus())
                    .FoodId(savedMeal.getFoodId())
                    .UserId(savedMeal.getUserId())
                    .updatedAt(savedMeal.getUpdatedAt())
                    .createdAt(savedMeal.getCreatedAt())
                    .build();

            return MealResponse.builder()
                    .resultCode("00322")
                    .resultMessage(MealResponse.ResultMessage.builder()
                            .en("Add meal plan successfull")
                            .vn("Thêm kế hoạch bữa ăn thành công")
                            .build())
                    .newPlan(newPlan)
                    .build();

        } catch (RuntimeException e) {
            return MealResponse.builder()
                    .resultCode("1999")
                    .resultMessage(MealResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
    @Override
    public MealDeleteResponse deleteMeal(DeleteMealRequest request) {
        try {
            Meal meal = mealRepository.findById(request.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Meal plan not found with id: " + request.getPlanId()));

            mealRepository.delete(meal);

            return MealDeleteResponse.builder()
                    .resultCode("00330")
                    .resultMessage(MealDeleteResponse.ResultMessage.builder()
                            .en("Your meal plan was deleted successfully.")
                            .vn("Kế hoạch bữa ăn của bạn đã được xóa thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return MealDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(MealDeleteResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }}
