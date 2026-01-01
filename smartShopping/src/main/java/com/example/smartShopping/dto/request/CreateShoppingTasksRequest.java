package com.example.smartShopping.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateShoppingTasksRequest {
    private Long listId;
    private List<TaskItem> tasks;

    @Data
    public static class TaskItem {
        private String foodName;
        private Integer quantity;
    }
}
