package com.example.smartShopping.service.impl;



//import com.example.smartShopping.dto.*;
import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.request.UpdateFoodRequest;
import com.example.smartShopping.dto.response.FoodDeleteResponse;
import com.example.smartShopping.dto.response.FoodResponse;
import com.example.smartShopping.dto.response.FoodUpdateResponse;
import com.example.smartShopping.entity.Category;
import com.example.smartShopping.entity.Food;
import com.example.smartShopping.entity.Unit;
import com.example.smartShopping.repository.CategoryRepository;
import com.example.smartShopping.repository.FoodRepository;
import com.example.smartShopping.repository.UnitRepository;
import com.example.smartShopping.service.FoodService;
import com.example.smartShopping.service.ImageStorageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final ImageStorageService imageStorageService;
    private final CategoryRepository categoryRepository; // thêm
    private final UnitRepository unitRepository;

    @Override
    public FoodResponse createFood(FoodRequest req, Long userId) {

        String imageUrl = null;
        if (req.getImageUrl() != null && !req.getImageUrl().isBlank()) {
            imageUrl = req.getImageUrl();
        }

        Food food = Food.builder()
                .name(req.getName())
                .foodCategoryId( mapCategory(req.getFoodCategoryName()) )
                .unitOfMeasurementId( mapUnit(req.getUnitName()) )
                .userId(userId)
                .groupId(req.getGroupId())
                .type("ingredient")
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();

        foodRepository.save(food);

        return FoodResponse.builder()
                .resultCode("00160")
                .resultMessage(
                        new FoodResponse.ResultMessage("Food creation successful", "Tạo thực phẩm thành công")
                )
                .newFood(
                        new FoodResponse.NewFood(
                                food.getId(),
                                food.getName(),
                                food.getType(),
                                food.getImageUrl(),
                                food.getUnitOfMeasurementId(),
                                food.getFoodCategoryId(),
                                food.getUserId(),
                                food.getGroupId(),
                                food.getCreatedAt(),
                                food.getUpdatedAt()
                        )
                )
                .build();
    }

    private Long mapCategory(String name) {
        if (name == null || name.isBlank()) return null;
        return categoryRepository.findByName(name)
                .map(Category::getId)
                .orElse(null);
    }

    private Long mapUnit(String name) {
        if (name == null || name.isBlank()) return null;
        return unitRepository.findByUnitName(name)
                .map(Unit::getId)
                .orElse(null);
    }


    @Override
    public FoodUpdateResponse updateFood(UpdateFoodRequest req, Long userId) {

        Food food = foodRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        if (req.getName() != null && !req.getName().isBlank()) {
            food.setName(req.getName());
        }

        if (req.getNewCategory() != null && !req.getNewCategory().isBlank()) {
            food.setFoodCategoryId(mapCategory(req.getNewCategory()));
        }

        if (req.getNewUnit() != null && !req.getNewUnit().isBlank()) {
            food.setUnitOfMeasurementId(mapUnit(req.getNewUnit()));
        }

        if (req.getGroupId() != null) {
            food.setGroupId(req.getGroupId());
        }

        // Update image bằng URL
        if (req.getImageUrl() != null && !req.getImageUrl().isBlank()) {
            food.setImageUrl(req.getImageUrl());
        }

        food.setUpdatedAt(LocalDateTime.now().toString());
        foodRepository.save(food);

        return FoodUpdateResponse.builder()
                .resultCode("00178")
                .resultMessage(new FoodUpdateResponse.ResultMessage("Successfully", "Thành công"))
                .food(
                        FoodUpdateResponse.FoodDto.builder()
                                .id(food.getId())
                                .name(food.getName())
                                .imageUrl(food.getImageUrl())
                                .type(food.getType())
                                .createdAt(food.getCreatedAt())
                                .updatedAt(food.getUpdatedAt())
                                .FoodCategoryId(food.getFoodCategoryId())
                                .UserId(food.getUserId())
                                .groupId(food.getGroupId())
                                .UnitOfMeasurementId(food.getUnitOfMeasurementId())
                                .build()
                ).build();
    }

    @Override
    @Transactional
    public FoodDeleteResponse deleteFood(String name, Long userId) {

        Food food = foodRepository.findByNameAndUserId(name, userId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        foodRepository.delete(food);

        return FoodDeleteResponse.builder()
                .resultCode("00184")
                .resultMessage(
                        FoodDeleteResponse.ResultMessage.builder()
                                .en("Food deletion successfull")
                                .vn("Xóa thực phẩm thành công")
                                .build()
                )
                .build();
    }
    @Override
    public FoodResponse getAllFoods() {
        List<Food> foods = foodRepository.findAll();

        List<FoodResponse.NewFood> foodList = foods.stream()
                .map(food -> new FoodResponse.NewFood(
                        food.getId(),
                        food.getName(),
                        food.getType(),
                        food.getImageUrl(),
                        food.getUnitOfMeasurementId(),
                        food.getFoodCategoryId(),
                        food.getUserId(),
                        food.getGroupId(),
                        food.getCreatedAt(),
                        food.getUpdatedAt()
                ))
                .toList();

        return FoodResponse.builder()
                .resultCode("00188")
                .resultMessage(new FoodResponse.ResultMessage(
                        "Successfull retrieve all foods",
                        "Lấy danh sách thực phẩm thành công"
                ))
                .foods(foodList)
                .build();
    }

    @Override
    public FoodResponse getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));

        FoodResponse.NewFood foodData = new FoodResponse.NewFood(
                food.getId(),
                food.getName(),
                food.getType(),
                food.getImageUrl(),
                food.getUnitOfMeasurementId(),
                food.getFoodCategoryId(),
                food.getUserId(),
                food.getGroupId(),
                food.getCreatedAt(),
                food.getUpdatedAt()
        );

        return FoodResponse.builder()
                .resultCode("00190")
                .resultMessage(new FoodResponse.ResultMessage(
                        "Successfully retrieved food details",
                        "Lấy thông tin thực phẩm thành công"
                ))
                .newFood(foodData)
                .build();
    }

    @Override
    public FoodResponse searchFoods(String keyword) {
        List<Food> foods = foodRepository.findByNameContainingIgnoreCase(keyword);

        List<FoodResponse.NewFood> foodList = foods.stream()
                .map(food -> new FoodResponse.NewFood(
                        food.getId(),
                        food.getName(),
                        food.getType(),
                        food.getImageUrl(),
                        food.getUnitOfMeasurementId(),
                        food.getFoodCategoryId(),
                        food.getUserId(),
                        food.getGroupId(),
                        food.getCreatedAt(),
                        food.getUpdatedAt()
                ))
                .toList();

        return FoodResponse.builder()
                .resultCode("00192")
                .resultMessage(new FoodResponse.ResultMessage(
                        "Successfully searched foods",
                        "Tìm kiếm thực phẩm thành công"
                ))
                .foods(foodList)
                .build();
    }

    @Override
    public FoodResponse getFoodsByGroupId(Long groupId) {
        List<Food> foods = foodRepository.findByGroupId(groupId);

        List<FoodResponse.NewFood> foodList = foods.stream()
                .map(food -> new FoodResponse.NewFood(
                        food.getId(),
                        food.getName(),
                        food.getType(),
                        food.getImageUrl(),
                        food.getUnitOfMeasurementId(),
                        food.getFoodCategoryId(),
                        food.getUserId(),
                        food.getGroupId(),
                        food.getCreatedAt(),
                        food.getUpdatedAt()
                ))
                .toList();

        return FoodResponse.builder()
                .resultCode("00194")
                .resultMessage(new FoodResponse.ResultMessage(
                        "Successfully retrieved foods by group",
                        "Lấy danh sách thực phẩm theo nhóm thành công"
                ))
                .foods(foodList)
                .build();
    }

}
