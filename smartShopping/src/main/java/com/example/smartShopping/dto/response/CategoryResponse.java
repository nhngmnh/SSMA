package com.example.smartShopping.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private ResultMessage resultMessage;
    private String resultCode;
    private List<CategoryDto> categories; // list tất cả category

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class CategoryDto {
        private Long id;
        private String name;
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
