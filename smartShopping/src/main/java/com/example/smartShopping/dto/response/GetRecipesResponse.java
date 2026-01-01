package com.example.smartShopping.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class GetRecipesResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private List<RecipeDto> recipes;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class RecipeDto {
        private Long id;
        private String name;
        private String description;
        private String htmlContent;
        private String createdAt;
        private String updatedAt;
        private Long FoodId;
    }
}
