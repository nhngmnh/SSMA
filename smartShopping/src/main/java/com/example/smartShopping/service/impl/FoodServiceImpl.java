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

        String imageUrl;
        if (req.getImage() != null && !req.getImage().isEmpty()) {
            imageUrl = imageStorageService.uploadImage(req.getImage(), userId);
        } else if (req.getImageUrl() != null && !req.getImageUrl().isBlank()) {
            imageUrl = req.getImageUrl();
        } else {
            throw new RuntimeException("Phải cung cấp image (file) hoặc imageUrl đã được upload trước");
        }

        Food food = Food.builder()
                .name(req.getName())
                .FoodCategoryId( mapCategory(req.getFoodCategoryName()) )
                .UnitOfMeasurementId( mapUnit(req.getUnitName()) )
                .userId(userId)
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
                                food.getCreatedAt(),
                                food.getUpdatedAt()
                        )
                )
                .build();
    }

    private Long mapCategory(String name) {
        if (name == null || name.isBlank()) return 2L; // default id
        return categoryRepository.findByName(name)
                .map(Category::getId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + name));
    }

    private Long mapUnit(String name) {
        if (name == null || name.isBlank()) return 2L; // default id
        return unitRepository.findByUnitName(name)
                .map(Unit::getId)
                .orElseThrow(() -> new RuntimeException("Unit not found: " + name));
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

        // Update image bằng file
        if (req.getImage() != null && !req.getImage().isEmpty()) {
            String newImageUrl = imageStorageService.uploadImage(req.getImage(), userId);
            food.setImageUrl(newImageUrl);
        }

        // Update image bằng URL (ưu tiên nếu người dùng gửi link)
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

}
