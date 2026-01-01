package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.CreateShoppingTasksRequest;

public interface ShoppingTaskService {
    void createTasks(CreateShoppingTasksRequest request, String username);
}
