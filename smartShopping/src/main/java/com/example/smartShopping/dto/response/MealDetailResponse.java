package com.example.smartShopping.dto.response;

import com.example.smartShopping.dto.MealFoodItemDto;
import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class MealDetailResponse {
    
    private ResultMessage resultMessage;
    private String resultCode;
    private MealDto meal;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class MealDto {
        private Long id;
        private String name;
        private String timestamp;
        private String status;
        private List<MealFoodItemDto> foodItems;
        private Long userId;
        private String updatedAt;
        private String createdAt;
    }
}
