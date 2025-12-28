package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CreateShoppingListRequest;
import com.example.smartShopping.dto.request.UpdateShoppingListRequest;
import com.example.smartShopping.dto.response.ShoppingListResponse;
import com.example.smartShopping.entity.ShoppingList;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.ShoppingListRepository;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;

    @Override
    public ShoppingListResponse create(CreateShoppingListRequest request, String username) {


        User creator = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found with username: " + username)
                );

        User assignedUser = userRepository
                .findByUsername(request.getAssignToUsername())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        ShoppingList list = new ShoppingList();
        list.setName(request.getName());
        list.setNote(request.getNote());
        list.setDate(parseDate(request.getDate()));
        list.setUser(creator);
        list.setAssignedToUser(assignedUser);
        list.setBelongsToGroupAdminId(creator.getId());

        shoppingListRepository.save(list);

        return mapToResponse(list);
    }

    @Override
    public ShoppingListResponse update(UpdateShoppingListRequest request, User updater) {

        ShoppingList list = shoppingListRepository.findById(request.getListId())
                .orElseThrow(() -> new RuntimeException("Shopping list not found"));

        if (request.getNewName() != null)
            list.setName(request.getNewName());

        if (request.getNewNote() != null)
            list.setNote(request.getNewNote());

        if (request.getNewDate() != null)
            list.setDate(parseDate(request.getNewDate()));

        if (request.getNewAssignToUsername() != null) {
            User newAssigned = userRepository
                    .findByUsername(request.getNewAssignToUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            list.setAssignedToUser(newAssigned);
        }

        shoppingListRepository.save(list);
        return mapToResponse(list);
    }

    @Override
    public void delete(Long listId, User requester) {
        ShoppingList list = shoppingListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Shopping list not found"));

        shoppingListRepository.delete(list);
    }

    // ================= helper =================

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(date, formatter);
    }

    private ShoppingListResponse mapToResponse(ShoppingList list) {
        return new ShoppingListResponse(
                list.getId(),
                list.getName(),
                list.getNote(),

                // belongsToGroupAdminId
                list.getBelongsToGroupAdminId(),

                // assignedToUserId
                list.getAssignedToUser() != null
                        ? list.getAssignedToUser().getId()
                        : null,

                list.getDate() != null
                        ? list.getDate().toString()
                        : null,

                list.getCreatedAt(),
                list.getUpdatedAt(),

                // userId (creator)
                list.getUser() != null
                        ? list.getUser().getId()
                        : null,

                // username (creator)
                list.getUser() != null
                        ? list.getUser().getUsername()
                        : null,

                // details (create/update thì chưa có task)
                List.of()
        );
    }



}
