package com.example.smartShopping.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UnitResponse {

    private Long id;
    private String unitName;
    private String createdAt;
      private String updatedAt;

    private ResultMessage resultMessage;
    private String resultCode;
    private List<UnitDto> units;

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    public static class UnitDto {
        private Long id;
        private String unitName;
        private String createdAt; // phải là String, convert từ LocalDateTime
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
