package com.example.smartShopping.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DeleteMealRequest {
    private Long planId;
}
