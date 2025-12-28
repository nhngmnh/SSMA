package com.example.smartShopping.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShoppingListRequest {
    private String name;
    private String assignToUsername;
    private String note;
    private String date; // mm/dd/yyyy
}
