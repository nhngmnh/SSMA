package com.example.smartShopping.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MealRequest {
    private String name;
    private String timestamp;
    private Long foodId;
}
