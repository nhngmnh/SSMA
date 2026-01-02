package com.example.smartShopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListUpdateResponse {

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
        private String date;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long userId;
        private String username;
    }
}
