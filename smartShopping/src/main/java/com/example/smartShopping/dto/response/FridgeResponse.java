package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.Food;
import com.example.smartShopping.entity.Fridge;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;

@Data
@Builder
public class FridgeResponse {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime expiredDate;
    private Integer quantity;
    private String note;
    private Long foodId;
    private Long userId;
    private FoodResponse.NewFood food; // <- thay vì FoodResponse




    public static FridgeResponse fromEntity(Fridge fridge) {
        return FridgeResponse.builder()
                .id(fridge.getId())
                .startDate(fridge.getStartDate())
                .expiredDate(fridge.getExpiredDate())
                .quantity(fridge.getQuantity())
                .note(fridge.getNote())
                .foodId(fridge.getFood().getId())
                .userId(fridge.getUser().getId())
                .food(FoodResponse.toNewFood(fridge.getFood()))
                .build();
    }

}
