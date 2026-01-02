package com.example.smartShopping.dto.response;


import com.example.smartShopping.entity.Food;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class FoodResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private NewFood newFood;

    private List<NewFood> foods; // thêm dòng này cho GET all

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class NewFood {
        private Long id;
        private String name;
        private String type;
        private String imageUrl;
        private Long UnitOfMeasurementId;
        private Long FoodCategoryId;
        private Long UserId;
        private Long groupId;
        private String createdAt;
        private String updatedAt;

        private CategoryResponse.CategoryDto FoodCategory;
        private UnitResponse.UnitDto unitOfMeasurement;

        // Nếu Lombok không tự generate constructor, thêm thủ công:
        public NewFood(Long id, String name, String type, String imageUrl,
                       Long UnitOfMeasurementId, Long FoodCategoryId, Long UserId,
                       Long groupId, String createdAt, String updatedAt) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.imageUrl = imageUrl;
            this.UnitOfMeasurementId = UnitOfMeasurementId;
            this.FoodCategoryId = FoodCategoryId;
            this.UserId = UserId;
            this.groupId = groupId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
//        private CategoryResponse FoodCategory;  // <- thêm
//        private UnitResponse unitOfMeasurement;  // <- thêm

    }
    public static FoodResponse fromEntities(List<Food> foodList) {
        List<NewFood> foods = foodList.stream()
                .map(food -> NewFood.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .type(food.getType())
                        .imageUrl(food.getImageUrl())
                        .UnitOfMeasurementId(food.getUnitOfMeasurementId())
                        .FoodCategoryId(food.getFoodCategoryId())
                        .UserId(food.getUserId())
                        .groupId(food.getGroupId())
                        .createdAt(food.getCreatedAt())
                        .updatedAt(food.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return FoodResponse.builder()
                .resultMessage(new ResultMessage("Success", "Thành công"))
                .resultCode("00231")
                .foods(foods)
                .build();
    }
    public static NewFood toNewFood(Food food) {
        return NewFood.builder()
                .id(food.getId())
                .name(food.getName())
                .type(food.getType())
                .imageUrl(food.getImageUrl())
                .FoodCategoryId(food.getFoodCategoryId())
                .UserId(food.getUserId())
                .groupId(food.getGroupId())
                .UnitOfMeasurementId(food.getUnitOfMeasurementId())
                // .FoodCategory(CategoryResponse.toDto(food.getFoodCategory())) // Food entity doesn't have these relationships
                // .unitOfMeasurement(UnitResponse.toDto(food.getUnitOfMeasurement())) // Only IDs are stored
                .createdAt(food.getCreatedAt())
                .updatedAt(food.getUpdatedAt())
                .build();
    }




}
