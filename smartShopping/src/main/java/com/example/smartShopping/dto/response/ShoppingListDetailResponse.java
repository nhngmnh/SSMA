package com.example.smartShopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDetailResponse {
    private String resultCode;
    private ResultMessage resultMessage;
    private ShoppingListDto shoppingList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShoppingListDto {
        private Long id;
        private String name;
        private String note;
        private Long belongsToGroupAdminId;
        private Long assignedToUserId;
        private String assignedToUsername;
        private String date;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long userId;
        private String username;
        private List<ItemDto> items;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDto {
        private Long id;
        private String foodName;
        private Integer quantity;
        private Boolean completed;
        private Long foodId;
    }
}
