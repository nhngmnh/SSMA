package com.example.smartShopping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingListResponse {

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


    private List<ShoppingTaskResponse> details;
}