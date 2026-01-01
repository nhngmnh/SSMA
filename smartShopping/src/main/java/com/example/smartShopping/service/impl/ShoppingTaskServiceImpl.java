package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CreateShoppingTasksRequest;
import com.example.smartShopping.dto.response.ShoppingListResponse;
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
    public void createTasks(CreateShoppingTasksRequest request, String username) {

        User creator = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingList list = shoppingListRepository.findById(request.getListId())
                .orElseThrow(() -> new RuntimeException("Shopping list not found"));

        // 🔐 chỉ người tạo list được thêm task
        if (!list.getUser().getId().equals(creator.getId())) {
            throw new RuntimeException("Only creator can add tasks");
        }

        List<ShoppingTask> tasks = request.getTasks().stream()
                .map(t -> {
                    Food food = foodRepository.findByNameIgnoreCase(t.getFoodName())
                            .orElseThrow(() -> new RuntimeException("Food not found: " + t.getFoodName()));

                    return ShoppingTask.builder()
                            .food(food) // map Food entity
                            .foodName(food.getName()) // vẫn lưu tên nếu cần
                            .quantity(t.getQuantity())
                            .completed(false)
                            .shoppingList(list)
                            .build();
                })
                .toList();


        taskRepository.saveAll(tasks);
    }


}
