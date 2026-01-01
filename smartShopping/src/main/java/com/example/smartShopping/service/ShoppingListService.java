package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.CreateShoppingListRequest;
import com.example.smartShopping.dto.request.UpdateShoppingListRequest;
import com.example.smartShopping.dto.response.ShoppingListResponse;
import com.example.smartShopping.entity.User;

public interface ShoppingListService {
    ShoppingListResponse create(CreateShoppingListRequest request, String username);
    ShoppingListResponse update(UpdateShoppingListRequest request, User updater);
    void delete(Long listId, User requester);
}

