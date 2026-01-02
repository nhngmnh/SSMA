package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.AddShoppingItemRequest;
import com.example.smartShopping.dto.request.CreateShoppingListRequest;
import com.example.smartShopping.dto.request.UpdateShoppingItemRequest;
import com.example.smartShopping.dto.request.UpdateShoppingListRequest;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.Food;
import com.example.smartShopping.entity.ShoppingList;
import com.example.smartShopping.entity.ShoppingTask;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.FoodRepository;
import com.example.smartShopping.repository.ShoppingListRepository;
import com.example.smartShopping.repository.ShoppingTaskRepository;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    private final ShoppingTaskRepository shoppingTaskRepository;
    private final FoodRepository foodRepository;

    @Override
    public ShoppingListCreateResponse create(CreateShoppingListRequest request, String username) {
        try {
            User creator = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

            User assignedUser = userRepository.findByUsername(request.getAssignToUsername())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));

            ShoppingList list = new ShoppingList();
            list.setName(request.getName());
            list.setNote(request.getNote());
            list.setDate(parseDate(request.getDate()));
            list.setUserId(creator.getId());
            list.setAssignedToUserId(assignedUser.getId());
            list.setBelongsToGroupAdminId(creator.getId());

            shoppingListRepository.save(list);

            ShoppingListCreateResponse.ShoppingListDto dto = ShoppingListCreateResponse.ShoppingListDto.builder()
                    .id(list.getId())
                    .name(list.getName())
                    .note(list.getNote())
                    .belongsToGroupAdminId(list.getBelongsToGroupAdminId())
                    .assignedToUserId(list.getAssignedToUserId())
                    .date(list.getDate() != null ? list.getDate().toString() : null)
                    .createdAt(list.getCreatedAt())
                    .updatedAt(list.getUpdatedAt())
                    .userId(list.getUserId())
                    .username(creator.getUsername())
                    .build();

            return ShoppingListCreateResponse.builder()
                    .resultCode("00130")
                    .resultMessage(ShoppingListCreateResponse.ResultMessage.builder()
                            .en("Shopping list created successfully")
                            .vn("Tạo danh sách mua sắm thành công")
                            .build())
                    .shoppingList(dto)
                    .build();

        } catch (RuntimeException e) {
            return ShoppingListCreateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListCreateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public ShoppingListUpdateResponse update(UpdateShoppingListRequest request, User updater) {
        try {
            ShoppingList list = shoppingListRepository.findById(request.getListId())
                    .orElseThrow(() -> new RuntimeException("Shopping list not found"));

            if (request.getNewName() != null)
                list.setName(request.getNewName());

            if (request.getNewNote() != null)
                list.setNote(request.getNewNote());

            if (request.getNewDate() != null)
                list.setDate(parseDate(request.getNewDate()));

            if (request.getNewAssignToUsername() != null) {
                User newAssigned = userRepository.findByUsername(request.getNewAssignToUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                list.setAssignedToUserId(newAssigned.getId());
            }

            shoppingListRepository.save(list);

            // Lấy thông tin user để trả về username
            User creator = userRepository.findById(list.getUserId()).orElse(null);
            User assignedUser = list.getAssignedToUserId() != null ? 
                    userRepository.findById(list.getAssignedToUserId()).orElse(null) : null;

            ShoppingListUpdateResponse.ShoppingListDto dto = ShoppingListUpdateResponse.ShoppingListDto.builder()
                    .id(list.getId())
                    .name(list.getName())
                    .note(list.getNote())
                    .belongsToGroupAdminId(list.getBelongsToGroupAdminId())
                    .assignedToUserId(list.getAssignedToUserId())
                    .date(list.getDate() != null ? list.getDate().toString() : null)
                    .createdAt(list.getCreatedAt())
                    .updatedAt(list.getUpdatedAt())
                    .userId(list.getUserId())
                    .username(creator != null ? creator.getUsername() : null)
                    .build();

            return ShoppingListUpdateResponse.builder()
                    .resultCode("00136")
                    .resultMessage(ShoppingListUpdateResponse.ResultMessage.builder()
                            .en("Shopping list updated successfully")
                            .vn("Cập nhật danh sách mua sắm thành công")
                            .build())
                    .shoppingList(dto)
                    .build();

        } catch (RuntimeException e) {
            return ShoppingListUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListUpdateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public ShoppingListDeleteResponse delete(Long listId, User requester) {
        try {
            ShoppingList list = shoppingListRepository.findById(listId)
                    .orElseThrow(() -> new RuntimeException("Shopping list not found"));

            shoppingListRepository.delete(list);

            return ShoppingListDeleteResponse.builder()
                    .resultCode("00142")
                    .resultMessage(ShoppingListDeleteResponse.ResultMessage.builder()
                            .en("Shopping list deleted successfully")
                            .vn("Xóa danh sách mua sắm thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return ShoppingListDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListDeleteResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(date, formatter);
    }

    @Override
    public ShoppingListGetAllResponse getAll(Long userId) {
        try {
            List<ShoppingList> lists = shoppingListRepository.findAll();
            
            List<ShoppingListGetAllResponse.ShoppingListDto> listDtos = lists.stream()
                    .map(list -> {
                        List<ShoppingTask> tasks = shoppingTaskRepository.findByShoppingListId(list.getId());
                        int totalItems = tasks.size();
                        int completedItems = (int) tasks.stream().filter(ShoppingTask::getCompleted).count();
                        
                        // Lấy thông tin user
                        User creator = list.getUserId() != null ? userRepository.findById(list.getUserId()).orElse(null) : null;
                        User assignedUser = list.getAssignedToUserId() != null ? 
                                userRepository.findById(list.getAssignedToUserId()).orElse(null) : null;
                        
                        return ShoppingListGetAllResponse.ShoppingListDto.builder()
                                .id(list.getId())
                                .name(list.getName())
                                .note(list.getNote())
                                .belongsToGroupAdminId(list.getBelongsToGroupAdminId())
                                .assignedToUserId(list.getAssignedToUserId())
                                .assignedToUsername(assignedUser != null ? assignedUser.getUsername() : null)
                                .date(list.getDate() != null ? list.getDate().toString() : null)
                                .createdAt(list.getCreatedAt())
                                .updatedAt(list.getUpdatedAt())
                                .userId(list.getUserId())
                                .username(creator != null ? creator.getUsername() : null)
                                .totalItems(totalItems)
                                .completedItems(completedItems)
                                .build();
                    })
                    .collect(Collectors.toList());

            return ShoppingListGetAllResponse.builder()
                    .resultCode("00200")
                    .resultMessage(ShoppingListGetAllResponse.ResultMessage.builder()
                            .en("Successfully retrieved shopping lists")
                            .vn("Lấy danh sách mua sắm thành công")
                            .build())
                    .shoppingLists(listDtos)
                    .build();

        } catch (Exception e) {
            return ShoppingListGetAllResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListGetAllResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public ShoppingListDetailResponse getById(Long listId, Long userId) {
        try {
            ShoppingList list = shoppingListRepository.findById(listId)
                    .orElseThrow(() -> new RuntimeException("Shopping list not found"));

            List<ShoppingTask> tasks = shoppingTaskRepository.findByShoppingListId(listId);
            
            List<ShoppingListDetailResponse.ItemDto> items = tasks.stream()
                    .map(task -> ShoppingListDetailResponse.ItemDto.builder()
                            .id(task.getId())
                            .foodName(task.getFoodName())
                            .quantity(task.getQuantity())
                            .completed(task.getCompleted())
                            .foodId(task.getFoodId())
                            .build())
                    .collect(Collectors.toList());

            // Lấy thông tin user
            User creator = list.getUserId() != null ? userRepository.findById(list.getUserId()).orElse(null) : null;
            User assignedUser = list.getAssignedToUserId() != null ? 
                    userRepository.findById(list.getAssignedToUserId()).orElse(null) : null;

            ShoppingListDetailResponse.ShoppingListDto dto = ShoppingListDetailResponse.ShoppingListDto.builder()
                    .id(list.getId())
                    .name(list.getName())
                    .note(list.getNote())
                    .belongsToGroupAdminId(list.getBelongsToGroupAdminId())
                    .assignedToUserId(list.getAssignedToUserId())
                    .assignedToUsername(assignedUser != null ? assignedUser.getUsername() : null)
                    .date(list.getDate() != null ? list.getDate().toString() : null)
                    .createdAt(list.getCreatedAt())
                    .updatedAt(list.getUpdatedAt())
                    .userId(list.getUserId())
                    .username(creator != null ? creator.getUsername() : null)
                    .items(items)
                    .build();

            return ShoppingListDetailResponse.builder()
                    .resultCode("00202")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("Successfully retrieved shopping list details")
                            .vn("Lấy chi tiết danh sách mua sắm thành công")
                            .build())
                    .shoppingList(dto)
                    .build();

        } catch (RuntimeException e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1404")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("Not found: " + e.getMessage())
                            .vn("Không tìm thấy: " + e.getMessage())
                            .build())
                    .build();
        } catch (Exception e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    @Transactional
    public ShoppingListDetailResponse addItem(Long listId, AddShoppingItemRequest request, Long userId) {
        try {
            ShoppingList list = shoppingListRepository.findById(listId)
                    .orElseThrow(() -> new RuntimeException("Shopping list not found"));

            ShoppingTask task = new ShoppingTask();
            task.setFoodName(request.getFoodName());
            task.setQuantity(request.getQuantity());
            task.setCompleted(false);
            task.setShoppingListId(listId);
            
            if (request.getFoodId() != null) {
                task.setFoodId(request.getFoodId());
            }

            shoppingTaskRepository.save(task);

            return getById(listId, userId);

        } catch (RuntimeException e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1400")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("Failed to add item: " + e.getMessage())
                            .vn("Thêm mục thất bại: " + e.getMessage())
                            .build())
                    .build();
        } catch (Exception e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    @Transactional
    public ShoppingListDetailResponse updateItem(Long listId, Long itemId, UpdateShoppingItemRequest request, Long userId) {
        try {
            ShoppingTask task = shoppingTaskRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Shopping task not found"));

            if (!task.getShoppingListId().equals(listId)) {
                throw new RuntimeException("Item does not belong to this shopping list");
            }

            if (request.getFoodName() != null) {
                task.setFoodName(request.getFoodName());
            }
            if (request.getQuantity() != null) {
                task.setQuantity(request.getQuantity());
            }
            if (request.getCompleted() != null) {
                task.setCompleted(request.getCompleted());
            }

            shoppingTaskRepository.save(task);

            return getById(listId, userId);

        } catch (RuntimeException e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1400")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("Failed to update item: " + e.getMessage())
                            .vn("Cập nhật mục thất bại: " + e.getMessage())
                            .build())
                    .build();
        } catch (Exception e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    @Transactional
    public ShoppingListDetailResponse deleteItem(Long listId, Long itemId, Long userId) {
        try {
            ShoppingTask task = shoppingTaskRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Shopping task not found"));

            if (!task.getShoppingListId().equals(listId)) {
                throw new RuntimeException("Item does not belong to this shopping list");
            }

            shoppingTaskRepository.delete(task);

            return getById(listId, userId);

        } catch (RuntimeException e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1400")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("Failed to delete item: " + e.getMessage())
                            .vn("Xóa mục thất bại: " + e.getMessage())
                            .build())
                    .build();
        } catch (Exception e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    @Transactional
    public ShoppingListDetailResponse completeList(Long listId, Long userId) {
        try {
            ShoppingList list = shoppingListRepository.findById(listId)
                    .orElseThrow(() -> new RuntimeException("Shopping list not found"));

            List<ShoppingTask> tasks = shoppingTaskRepository.findByShoppingListId(listId);
            
            // Mark all items as completed
            tasks.forEach(task -> {
                task.setCompleted(true);
                shoppingTaskRepository.save(task);
            });

            return getById(listId, userId);

        } catch (RuntimeException e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1400")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("Failed to complete list: " + e.getMessage())
                            .vn("Hoàn thành danh sách thất bại: " + e.getMessage())
                            .build())
                    .build();
        } catch (Exception e) {
            return ShoppingListDetailResponse.builder()
                    .resultCode("1999")
                    .resultMessage(ShoppingListDetailResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
}
