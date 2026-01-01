package com.example.smartShopping.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateMealRequest {
    private Long planId;
    private String name;
    private String timestamp;
    private String status;
    private Long foodId;
}
