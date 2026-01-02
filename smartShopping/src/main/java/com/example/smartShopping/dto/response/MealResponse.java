package com.example.smartShopping.dto.response;

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
        private List<Long> recipeIds; // Danh sách recipe IDs
        private Long UserId;
        private Long groupId;
        private String updatedAt;
        private String createdAt;
    }
}
