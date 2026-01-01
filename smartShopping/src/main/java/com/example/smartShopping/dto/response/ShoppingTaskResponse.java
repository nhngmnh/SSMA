package com.example.smartShopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingTaskResponse {
    private Long id;
    private String name;
    private String quantity;
}
