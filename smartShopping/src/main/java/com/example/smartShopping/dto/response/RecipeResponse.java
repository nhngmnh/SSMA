package com.example.smartShopping.dto.response;

import lombok.*;
import java.util.List;

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
        private List<Long> foodIds; // Danh sách food IDs (nguyên liệu)
        private String createdAt;
        private String updatedAt;
    }
}
