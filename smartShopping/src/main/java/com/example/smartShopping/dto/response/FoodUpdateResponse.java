package com.example.smartShopping.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodUpdateResponse {

    private ResultMessage resultMessage;

    private String resultCode;

    private FoodDto food;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FoodDto {
        private Long id;
        private String name;
        private String imageUrl;
        private String type;
        private String createdAt;
        private String updatedAt;
        private Long FoodCategoryId;
        private Long UserId;
        private Long UnitOfMeasurementId;
    }
}
