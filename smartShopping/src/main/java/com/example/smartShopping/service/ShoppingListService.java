package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.AddShoppingItemRequest;
import com.example.smartShopping.dto.request.CreateShoppingListRequest;
import com.example.smartShopping.dto.request.UpdateShoppingItemRequest;
import com.example.smartShopping.dto.request.UpdateShoppingListRequest;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.User;

import java.util.List;

public interface ShoppingListService {
    ShoppingListCreateResponse create(CreateShoppingListRequest request, String username);
    ShoppingListGetAllResponse getAll(Long userId);
    ShoppingListDetailResponse getById(Long listId, Long userId);
    ShoppingListUpdateResponse update(UpdateShoppingListRequest request, User updater);
    ShoppingListDeleteResponse delete(Long listId, User requester);
    ShoppingListDetailResponse addItem(Long listId, AddShoppingItemRequest request, Long userId);
    ShoppingListDetailResponse updateItem(Long listId, Long itemId, UpdateShoppingItemRequest request, Long userId);
    ShoppingListDetailResponse deleteItem(Long listId, Long itemId, Long userId);
    ShoppingListDetailResponse completeList(Long listId, Long userId);
}

