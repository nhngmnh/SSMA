package com.example.smartShopping.dto.request;

import lombok.Data;

@Data
public class UpdateShoppingItemRequest {
    private String foodName;
    private Integer quantity;
    private Boolean completed;
}
