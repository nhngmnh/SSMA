package com.example.smartShopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFridgeRequest {
    private String foodName;
    private Integer useWithin; // phút
    private Integer quantity;
}
