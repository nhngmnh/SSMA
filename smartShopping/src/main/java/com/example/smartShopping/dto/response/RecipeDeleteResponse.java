package com.example.smartShopping.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class RecipeDeleteResponse {

    private ResultMessage resultMessage;
    private String resultCode;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }
}
