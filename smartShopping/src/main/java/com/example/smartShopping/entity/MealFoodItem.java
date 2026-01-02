package com.example.smartShopping.entity;

import lombok.*;

/**
 * Represents a food item with quantity in a meal plan
 * Used in JSONB column as part of meal_plans table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealFoodItem {
    private Long foodId;
    private Double quantity;  // Số lượng
    private String unitName;  // Đơn vị (kg, lít, cái, etc.)
}
