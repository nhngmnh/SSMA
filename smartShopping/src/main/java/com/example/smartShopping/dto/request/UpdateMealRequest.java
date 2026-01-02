package com.example.smartShopping.dto.request;

import com.example.smartShopping.dto.MealFoodItemDto;
import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateMealRequest {
    private Long planId;
    private String name;
    private String timestamp;
    private String status;
    private List<MealFoodItemDto> foodItems;
}
