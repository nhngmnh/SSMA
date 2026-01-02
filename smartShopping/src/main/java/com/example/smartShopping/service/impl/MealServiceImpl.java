package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.DeleteMealRequest;
import com.example.smartShopping.dto.request.MealRequest;
import com.example.smartShopping.dto.request.UpdateMealRequest;
import com.example.smartShopping.dto.response.MealDeleteResponse;
import com.example.smartShopping.dto.response.MealDetailResponse;
import com.example.smartShopping.dto.response.MealGetAllResponse;
import com.example.smartShopping.dto.response.MealResponse;
import com.example.smartShopping.dto.response.MealUpdateResponse;
import com.example.smartShopping.entity.Meal;
import com.example.smartShopping.repository.MealRepository;
import com.example.smartShopping.service.MealService;
import com.example.smartShopping.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final AuthorizationService authService;

    @Override
    public MealResponse createMeal(MealRequest request, Long userId, String authHeader) {
        try {
            // Xác định groupId
            Long groupId;
            
            // Nếu là admin và đã chọn groupId → dùng groupId đó
            if (authService.isAdmin(authHeader) && request.getGroupId() != null) {
                groupId = request.getGroupId();
                // Validate admin có quyền tạo cho group này
                authService.requireModifyGroupData(authHeader, groupId);
            } 
            // Nếu là thành viên thường → tự động lấy group của user
            else {
                groupId = authService.getUserGroupId(userId);
                if (groupId == null) {
                    throw new RuntimeException("User does not belong to any group");
                }
            }

            // Create timestamp in ISO format
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Create meal entity
            Meal meal = Meal.builder()
                    .name(request.getName())
                    .timestamp(request.getTimestamp())
                    .status("NOT_PASS_YET")
                    .recipeIds(request.getRecipeIds()) // Danh sách recipe IDs
                    .userId(userId)
                    .groupId(groupId)
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
                    .recipeIds(savedMeal.getRecipeIds())
                    .UserId(savedMeal.getUserId())
                    .groupId(savedMeal.getGroupId())
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
    public MealUpdateResponse updateMeal(UpdateMealRequest request) {
        try {
            Meal meal = mealRepository.findById(request.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Meal plan not found with id: " + request.getPlanId()));

            if (request.getName() != null) {
                meal.setName(request.getName());
            }
            if (request.getTimestamp() != null) {
                meal.setTimestamp(request.getTimestamp());
            }
            if (request.getStatus() != null) {
                meal.setStatus(request.getStatus());
            }
            if (request.getRecipeIds() != null) {
                meal.setRecipeIds(request.getRecipeIds());
            }

            meal.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            Meal updatedMeal = mealRepository.save(meal);

            MealUpdateResponse.UpdatedPlan mealDto = MealUpdateResponse.UpdatedPlan.builder()
                    .id(updatedMeal.getId())
                    .name(updatedMeal.getName())
                    .timestamp(updatedMeal.getTimestamp())
                    .status(updatedMeal.getStatus())
                    .recipeIds(updatedMeal.getRecipeIds())
                    .UserId(updatedMeal.getUserId())
                    .updatedAt(updatedMeal.getUpdatedAt())
                    .createdAt(updatedMeal.getCreatedAt())
                    .build();

            return MealUpdateResponse.builder()
                    .resultCode("00326")
                    .resultMessage(MealUpdateResponse.ResultMessage.builder()
                            .en("Meal plan updated successfully")
                            .vn("Cập nhật kế hoạch bữa ăn thành công")
                            .build())
                    .updatedPlan(mealDto)
                    .build();

        } catch (RuntimeException e) {
            return MealUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(MealUpdateResponse.ResultMessage.builder()
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
    }

    @Override
    public MealGetAllResponse getAllMeals() {
        try {
            List<Meal> meals = mealRepository.findAll();

            List<MealGetAllResponse.MealDto> mealDtos = meals.stream()
                    .map(meal -> MealGetAllResponse.MealDto.builder()
                            .id(meal.getId())
                            .name(meal.getName())
                            .timestamp(meal.getTimestamp())
                            .status(meal.getStatus())
                            .recipeIds(meal.getRecipeIds())
                            .userId(meal.getUserId())
                            .updatedAt(meal.getUpdatedAt())
                            .createdAt(meal.getCreatedAt())
                            .build())
                    .collect(Collectors.toList());

            return MealGetAllResponse.builder()
                    .resultCode("00334")
                    .resultMessage(MealGetAllResponse.ResultMessage.builder()
                            .en("Successfully retrieved meal plans")
                            .vn("Lấy danh sách kế hoạch bữa ăn thành công")
                            .build())
                    .meals(mealDtos)
                    .build();

        } catch (Exception e) {
            return MealGetAllResponse.builder()
                    .resultCode("1999")
                    .resultMessage(MealGetAllResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public MealDetailResponse getMealById(Long id) {
        try {
            Meal meal = mealRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meal plan not found with id: " + id));

            MealDetailResponse.MealDto mealDto = MealDetailResponse.MealDto.builder()
                    .id(meal.getId())
                    .name(meal.getName())
                    .timestamp(meal.getTimestamp())
                    .status(meal.getStatus())
                    .recipeIds(meal.getRecipeIds())
                    .userId(meal.getUserId())
                    .updatedAt(meal.getUpdatedAt())
                    .createdAt(meal.getCreatedAt())
                    .build();

            return MealDetailResponse.builder()
                    .resultCode("00336")
                    .resultMessage(MealDetailResponse.ResultMessage.builder()
                            .en("Successfully retrieved meal plan details")
                            .vn("Lấy chi tiết kế hoạch bữa ăn thành công")
                            .build())
                    .meal(mealDto)
                    .build();

        } catch (RuntimeException e) {
            return MealDetailResponse.builder()
                    .resultCode("1404")
                    .resultMessage(MealDetailResponse.ResultMessage.builder()
                            .en("Not found: " + e.getMessage())
                            .vn("Không tìm thấy: " + e.getMessage())
                            .build())
                    .build();
        } catch (Exception e) {
            return MealDetailResponse.builder()
                    .resultCode("1999")
                    .resultMessage(MealDetailResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
}
