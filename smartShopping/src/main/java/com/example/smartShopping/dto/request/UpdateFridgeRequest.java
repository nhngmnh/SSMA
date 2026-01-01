package com.example.smartShopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFridgeRequest {

    private Long itemId;

    private String newNote;

    private Integer newQuantity;

    private Integer newUseWithin; // phút

    private String newFoodName;
}
