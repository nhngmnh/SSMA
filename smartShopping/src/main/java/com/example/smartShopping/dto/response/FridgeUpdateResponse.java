package com.example.smartShopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FridgeUpdateResponse {

    private String resultCode;
    private ResultMessage resultMessage;
    private FridgeDto fridge;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FridgeDto {
        private Long id;
        private LocalDateTime startDate;
        private LocalDateTime expiredDate;
        private Integer quantity;
        private String note;
        private String foodName;
        private Long foodId;
    }
}
