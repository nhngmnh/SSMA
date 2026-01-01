package com.example.smartShopping.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateFoodRequest {

    private Long id; // foodId cần update

    private String name;

    private String newCategory;

    private String newUnit;

    private String imageUrl;       // link URL

}
