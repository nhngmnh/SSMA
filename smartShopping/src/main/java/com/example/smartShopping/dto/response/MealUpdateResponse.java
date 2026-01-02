package com.example.smartShopping.dto.response;

import lombok.*;
import java.util.List;

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
        private List<Long> recipeIds;
        private Long UserId;
        private String updatedAt;
        private String createdAt;
    }
}
