package com.example.smartShopping.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class RecipeResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private NewRecipe newRecipe;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class NewRecipe {
        private Long id;
        private String name;
        private String description;
        private String htmlContent;
        private String createdAt;
        private String updatedAt;
        private Long FoodId;

        
        @JsonProperty("Food.id")
        private Long foodDotId;
        
        @JsonProperty("Food.name")
        private String foodDotName;
        
        @JsonProperty("Food.imageUrl")
        private String foodDotImageUrl;
        
        @JsonProperty("Food.type")
        private String foodDotType;
        
        @JsonProperty("Food.createdAt")
        private String foodDotCreatedAt;
        
        @JsonProperty("Food.updatedAt")
        private String foodDotUpdatedAt;
        
        @JsonProperty("Food.FoodCategoryId")
        private Long foodDotFoodCategoryId;
        
        @JsonProperty("Food.UserId")
        private Long foodDotUserId;
        
        @JsonProperty("Food.UnitOfMeasurementId")
        private Long foodDotUnitOfMeasurementId;
    }
}
