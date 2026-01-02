package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CreateShoppingTasksRequest;
import com.example.smartShopping.dto.response.ShoppingTaskCreateResponse;
import com.example.smartShopping.entity.Food;
import com.example.smartShopping.entity.ShoppingList;
import com.example.smartShopping.entity.ShoppingTask;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.ShoppingTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.repository.ShoppingTaskRepository;
import com.example.smartShopping.repository.ShoppingListRepository;
import com.example.smartShopping.repository.FoodRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingTaskServiceImpl implements ShoppingTaskService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    private final ShoppingTaskRepository taskRepository;
    private final FoodRepository foodRepository;

    @Override
    public ShoppingTaskCreateResponse createTasks(CreateShoppingTasksRequest request, String username) {
        try {
            User creator = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ShoppingList list = shoppingListRepository.findById(request.getListId())
                    .orElseThrow(() -> new RuntimeException("Shopping list not found"));

            if (!list.getUserId().equals(creator.getId())) {
                throw new RuntimeException("Only creator can add tasks");
            }

            List<ShoppingTask> tasks = request.getTasks().stream()
                    .map(t -> {
                        Food food = foodRepository.findByNameIgnoreCase(t.getFoodName())
                                .orElseThrow(() -> new RuntimeException("Food not found: " + t.getFoodName()));

                        return ShoppingTask.builder()
                                .foodId(food.getId())
                                .foodName(food.getName())
                                .quantity(t.getQuantity())
                                .completed(false)
                                .shoppingListId(list.getId())
                                .build();
                    })
                    .toList();

            taskRepository.saveAll(tasks);

            return ShoppingTaskCreateResponse.builder()
                    .resultCode("00172")
                    .resultMessage(ShoppingTaskCreateResponse.ResultMessage.builder()
                            .en("Shopping tasks created successfully")
                            .vn("Tạo các nhiệm vụ mua sắm thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return ShoppingTaskCreateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingTaskCreateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
}
