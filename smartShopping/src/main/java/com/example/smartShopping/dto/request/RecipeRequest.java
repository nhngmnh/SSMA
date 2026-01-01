package com.example.smartShopping.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RecipeRequest {
    private String name;
    private String description;
    private String htmlContent;
    private Long foodId;
}
