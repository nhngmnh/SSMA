package com.example.smartShopping.dto.request;

import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RecipeRequest {
    private String name;
    private String description;
    private String htmlContent;
    private List<Long> foodIds; // Danh sách food IDs (nguyên liệu cần cho món ăn)
}
