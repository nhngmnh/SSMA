package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.Category;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    public static CategoryResponse fromEntities(List<Category> categories) {
        List<CategoryDto> dtos = categories.stream()
                .map(CategoryResponse::toDto)
                .collect(Collectors.toList());

        return CategoryResponse.builder()
                .resultMessage(new ResultMessage("Success", "Thành công"))
                .resultCode("002xx")
                .categories(dtos)
                .build();
    }
    // Thêm method static để mapping
    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updatedAt(category.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }



}
