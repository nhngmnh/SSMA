package com.example.smartShopping.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitCreateResponse {
    private ResultMessage resultMessage;
    private String resultCode;
    private UnitDto newUnit;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UnitDto {
        private Long id;
        private String unitName;
        private String createdAt;
        private String updatedAt;
    }

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
