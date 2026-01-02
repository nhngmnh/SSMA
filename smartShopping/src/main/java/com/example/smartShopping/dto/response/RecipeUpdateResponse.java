package com.example.smartShopping.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class RecipeUpdateResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private UpdatedRecipe updatedRecipe;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class UpdatedRecipe {
        private Long id;
        private String name;
        private String description;
        private String htmlContent;
        private String createdAt;
        private String updatedAt;
        private List<Long> foodIds;
    }
}
