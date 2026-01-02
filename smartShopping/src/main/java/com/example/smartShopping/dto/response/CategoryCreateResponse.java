package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.Category;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateResponse {
    private ResultMessage resultMessage;
    private String resultCode;
    private CategoryDto newCategory;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
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
