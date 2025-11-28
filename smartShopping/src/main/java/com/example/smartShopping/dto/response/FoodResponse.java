package com.example.smartShopping.dto.response;


import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class FoodResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private NewFood newFood;

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
        private String createdAt;
        private String updatedAt;
    }
}
