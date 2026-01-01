package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.Unit;
import lombok.*;

import java.time.format.DateTimeFormatter;
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
    public static UnitResponse fromEntity(Unit unit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return UnitResponse.builder()
                .id(unit.getId())
                .unitName(unit.getUnitName())
                .createdAt(unit.getCreatedAt().format(formatter))
                .updatedAt(unit.getUpdatedAt().format(formatter))
                .build();
    }
    public static UnitDto toDto(Unit unit) {
        return UnitDto.builder()
                .id(unit.getId())
                .unitName(unit.getUnitName())
                .createdAt(unit.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updatedAt(unit.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
