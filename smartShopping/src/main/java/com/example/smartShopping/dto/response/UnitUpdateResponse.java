package com.example.smartShopping.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitUpdateResponse {
    private ResultMessage resultMessage;
    private String resultCode;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }
}
