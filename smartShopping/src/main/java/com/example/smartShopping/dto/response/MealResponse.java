package com.example.smartShopping.dto.response;

import com.example.smartShopping.dto.MealFoodItemDto;
import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class MealResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private NewPlan newPlan;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class NewPlan {
        private Long id;
        private String name;
        private String timestamp;
        private String status;
        private List<MealFoodItemDto> foodItems; // Danh sách foods với quantity
        private Long UserId;
        private Long groupId;
        private String updatedAt;
        private String createdAt;
    }
}
