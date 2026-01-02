package com.example.smartShopping.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class FoodRequest {
    private String imageUrl;
    private String name;
    private String foodCategoryName;
    private String unitName;
    private Long groupId;
    private Double price;
    private Double quantity;
    private String expirationDate;
}

