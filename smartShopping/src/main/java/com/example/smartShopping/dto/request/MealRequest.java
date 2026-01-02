package com.example.smartShopping.dto.request;

import com.example.smartShopping.dto.MealFoodItemDto;
import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MealRequest {
    private String name;
    private String timestamp;
    private List<MealFoodItemDto> foodItems; // Danh sách foods với quantity
    private Long groupId; // Optional - chỉ admin mới có thể chọn, thành viên thường sẽ auto-assign
}
