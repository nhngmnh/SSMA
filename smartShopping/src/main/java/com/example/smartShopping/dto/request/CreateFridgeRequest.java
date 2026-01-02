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
    private Long groupId; // null nếu là personal data, có giá trị nếu là group data
}
