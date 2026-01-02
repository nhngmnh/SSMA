package com.example.smartShopping.dto.request;

import lombok.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MealRequest {
    private String name;
    private String timestamp;
    private List<Long> recipeIds; // Danh sách recipe IDs (nhiều món trong 1 bữa ăn)
    private Long groupId; // Optional - chỉ admin mới có thể chọn, thành viên thường sẽ auto-assign
}
