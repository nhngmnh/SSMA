package com.example.smartShopping.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodDeleteResponse {

    private String resultCode;
    private ResultMessage resultMessage;

    @Data
    @Builder
    public static class ResultMessage {
        private String vn;
        private String en;
    }
}
