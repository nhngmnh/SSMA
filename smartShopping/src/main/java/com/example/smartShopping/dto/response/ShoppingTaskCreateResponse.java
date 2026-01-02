package com.example.smartShopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingTaskCreateResponse {

    private String resultCode;
    private ResultMessage resultMessage;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultMessage {
        private String en;
        private String vn;
    }
}
