package com.example.smartShopping.dto;

import com.example.smartShopping.entity.MealFoodItem;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for food item with quantity in meal plan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealFoodItemDto {
    private Long foodId;
    private Double quantity;
    private String unitName;
    
    // Convert from entity to DTO
    public static MealFoodItemDto fromEntity(MealFoodItem entity) {
        if (entity == null) return null;
        return MealFoodItemDto.builder()
                .foodId(entity.getFoodId())
                .quantity(entity.getQuantity())
                .unitName(entity.getUnitName())
                .build();
    }
    
    // Convert from DTO to entity
    public MealFoodItem toEntity() {
        return MealFoodItem.builder()
                .foodId(this.foodId)
                .quantity(this.quantity)
                .unitName(this.unitName)
                .build();
    }
    
    // Convert list of entities to DTOs
    public static List<MealFoodItemDto> fromEntities(List<MealFoodItem> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(MealFoodItemDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    // Convert list of DTOs to entities
    public static List<MealFoodItem> toEntities(List<MealFoodItemDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(MealFoodItemDto::toEntity)
                .collect(Collectors.toList());
    }
}
