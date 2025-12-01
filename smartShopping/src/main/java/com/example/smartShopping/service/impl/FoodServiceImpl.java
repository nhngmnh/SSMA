package com.example.smartShopping.service.impl;



//import com.example.smartShopping.dto.*;
import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.response.FoodResponse;
import com.example.smartShopping.entity.Food;
import com.example.smartShopping.repository.FoodRepository;
import com.example.smartShopping.service.FoodService;
import com.example.smartShopping.service.ImageStorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final ImageStorageService imageStorageService;

    @Override
    public FoodResponse createFood(FoodRequest req, Long userId) {

        String imageUrl = imageStorageService.uploadImage(req.getImage(), userId);

        Food food = Food.builder()
                .name(req.getName())
                .FoodCategoryId( mapCategory(req.getFoodCategoryName()) )
                .UnitOfMeasurementId( mapUnit(req.getUnitName()) )
                .UserId(userId)
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
        // TODO: map tên → id
        return 2L;
    }

    private Long mapUnit(String name) {
        // TODO: map tên → id
        return 2L;
    }
}
