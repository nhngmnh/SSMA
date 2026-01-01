package com.example.smartShopping.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class MealUpdateResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private UpdatedPlan updatedPlan;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class UpdatedPlan {
        private Long id;
        private String name;
        private String timestamp;
        private String status;
        private Long FoodId;
        private Long UserId;
        private String updatedAt;
        private String createdAt;
    }
}
