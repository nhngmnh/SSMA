package com.example.smartShopping.dto.request;

import lombok.Data;

@Data
public class AddShoppingItemRequest {
    private String foodName;
    private Integer quantity;
    private Long foodId;
}
