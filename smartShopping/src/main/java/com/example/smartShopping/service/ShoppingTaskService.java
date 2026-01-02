package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.CreateShoppingTasksRequest;
import com.example.smartShopping.dto.response.ShoppingTaskCreateResponse;

public interface ShoppingTaskService {
    ShoppingTaskCreateResponse createTasks(CreateShoppingTasksRequest request, String username);
}
