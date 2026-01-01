package com.example.smartShopping.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateRecipeRequest {
    private Long id;
    private String name;
    private String description;
    private String htmlContent;
    private Long foodId;
}
