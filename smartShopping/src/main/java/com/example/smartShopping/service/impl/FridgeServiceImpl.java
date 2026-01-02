package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.FridgeCreateResponse;
import com.example.smartShopping.dto.response.FridgeUpdateResponse;
import com.example.smartShopping.dto.response.FridgeDeleteResponse;
import com.example.smartShopping.dto.response.FridgeGetAllResponse;
import com.example.smartShopping.entity.Food;
import com.example.smartShopping.entity.Fridge;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.repository.FoodRepository;
import com.example.smartShopping.repository.FridgeRepository;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.repository.GroupRepository;
import com.example.smartShopping.service.FridgeService;
import com.example.smartShopping.service.AuthorizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FridgeServiceImpl implements FridgeService {

    private final FridgeRepository fridgeRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AuthorizationService authService;

    @Override
    public FridgeCreateResponse createFridge(CreateFridgeRequest request, Long userId, String authHeader) {
        try {
            // Get user from userId parameter
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // If groupId is provided, validate user can create for this group
            if (request.getGroupId() != null) {
                authService.requireModifyGroupData(authHeader, request.getGroupId());
            }
            // If no groupId, this is personal data - validate user is creating for themselves
            else {
                authService.requireModifyPersonalData(authHeader, userId);
            }

            Food food = foodRepository.findByNameIgnoreCase(request.getFoodName())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime expiredDate = startDate.plusMinutes(request.getUseWithin());

            Fridge fridge = Fridge.builder()
                    .startDate(startDate)
                    .expiredDate(expiredDate)
                    .quantity(request.getQuantity())
                    .food(food)
                    .user(user)
                    .groupId(request.getGroupId())
                    .build();

            fridgeRepository.save(fridge);

            FridgeCreateResponse.FridgeDto dto = FridgeCreateResponse.FridgeDto.builder()
                    .id(fridge.getId())
                    .startDate(fridge.getStartDate())
                    .expiredDate(fridge.getExpiredDate())
                    .quantity(fridge.getQuantity())
                    .note(fridge.getNote())
                    .foodName(fridge.getFood().getName())
                    .foodId(fridge.getFood().getId())
                    .groupId(fridge.getGroupId())
                    .build();

            return FridgeCreateResponse.builder()
                    .resultCode("00148")
                    .resultMessage(FridgeCreateResponse.ResultMessage.builder()
                            .en("Fridge item created successfully")
                            .vn("Tạo mục tủ lạnh thành công")
                            .build())
                    .fridge(dto)
                    .build();

        } catch (RuntimeException e) {
            return FridgeCreateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(FridgeCreateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public FridgeUpdateResponse updateFridge(UpdateFridgeRequest request, String authHeader) {
        try {
            // Find the fridge item first
            Fridge fridge = fridgeRepository.findById(request.getItemId())
                    .orElseThrow(() -> new RuntimeException("Fridge item not found"));

            // Validate permission based on whether it's personal or group data
            if (fridge.getGroupId() != null) {
                authService.requireModifyGroupData(authHeader, fridge.getGroupId());
            } else {
                authService.requireModifyPersonalData(authHeader, fridge.getUser().getId());
            }

            if (request.getNewNote() != null) {
                fridge.setNote(request.getNewNote());
            }

            if (request.getNewQuantity() != null) {
                fridge.setQuantity(request.getNewQuantity());
            }

            if (request.getNewFoodName() != null) {
                Food food = foodRepository.findByNameIgnoreCase(request.getNewFoodName())
                        .orElseThrow(() -> new RuntimeException("Food not found"));
                fridge.setFood(food);
            }

            if (request.getNewUseWithin() != null) {
                fridge.setExpiredDate(fridge.getStartDate().plusMinutes(request.getNewUseWithin()));
            }

            fridgeRepository.save(fridge);

            FridgeUpdateResponse.FridgeDto dto = FridgeUpdateResponse.FridgeDto.builder()
                    .id(fridge.getId())
                    .startDate(fridge.getStartDate())
                    .expiredDate(fridge.getExpiredDate())
                    .quantity(fridge.getQuantity())
                    .note(fridge.getNote())
                    .foodName(fridge.getFood().getName())
                    .foodId(fridge.getFood().getId())
                    .groupId(fridge.getGroupId())
                    .build();

            return FridgeUpdateResponse.builder()
                    .resultCode("00154")
                    .resultMessage(FridgeUpdateResponse.ResultMessage.builder()
                            .en("Fridge item updated successfully")
                            .vn("Cập nhật mục tủ lạnh thành công")
                            .build())
                    .fridge(dto)
                    .build();

        } catch (RuntimeException e) {
            return FridgeUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(FridgeUpdateResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public FridgeDeleteResponse deleteFridgeItem(String foodName, String authHeader) {
        try {
            Long userId = authService.getUserIdFromAuth(authHeader);

            // Find items by userId and foodName
            List<Fridge> items = fridgeRepository.findByUserIdAndFood_NameIgnoreCase(userId, foodName);
            
            if (items.isEmpty()) {
                throw new RuntimeException("Fridge item not found: " + foodName);
            }

            // Validate permission for each item before deleting
            for (Fridge item : items) {
                if (item.getGroupId() != null) {
                    authService.requireModifyGroupData(authHeader, item.getGroupId());
                } else {
                    authService.requireModifyPersonalData(authHeader, item.getUser().getId());
                }
            }

            // Delete all matching items
            int deletedCount = fridgeRepository.deleteByUserIdAndFoodName(userId, foodName);

            return FridgeDeleteResponse.builder()
                    .resultCode("00160")
                    .resultMessage(FridgeDeleteResponse.ResultMessage.builder()
                            .en("Fridge item deleted successfully")
                            .vn("Xóa mục tủ lạnh thành công")
                            .build())
                    .build();

        } catch (RuntimeException e) {
            return FridgeDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(FridgeDeleteResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public FridgeGetAllResponse getAllFridgeItems(Long userId, String authHeader) {
        try {
            // Get user and their groups
            User user = authService.getUserFromAuth(authHeader);
            
            // Start with personal items
            List<Fridge> fridgeList = fridgeRepository.findByUserId(userId);

            // Add items from user's groups (where user is member or owner)
            List<GroupEntity> userGroups = groupRepository.findAllByMembersContainingOrAdminId(userId, userId);
            for (GroupEntity group : userGroups) {
                List<Fridge> groupItems = fridgeRepository.findByGroupId(group.getId());
                fridgeList.addAll(groupItems);
            }

            List<FridgeGetAllResponse.FridgeDto> fridgeDtos = fridgeList.stream()
                    .map(fridge -> FridgeGetAllResponse.FridgeDto.builder()
                            .id(fridge.getId())
                            .startDate(fridge.getStartDate())
                            .expiredDate(fridge.getExpiredDate())
                            .quantity(fridge.getQuantity())
                            .note(fridge.getNote())
                            .foodName(fridge.getFood().getName())
                            .foodId(fridge.getFood().getId())
                            .groupId(fridge.getGroupId())
                            .build())
                    .collect(Collectors.toList());

            return FridgeGetAllResponse.builder()
                    .resultCode("00166")
                    .resultMessage(FridgeGetAllResponse.ResultMessage.builder()
                            .en("Get fridge items successfully")
                            .vn("Lấy các mục tủ lạnh thành công")
                            .build())
                    .fridgeItems(fridgeDtos)
                    .build();

        } catch (RuntimeException e) {
            return FridgeGetAllResponse.builder()
                    .resultCode("1999")
                    .resultMessage(FridgeGetAllResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }

    @Override
    public FridgeGetAllResponse getFridgeItemsByFoodName(String foodName, Long userId, String authHeader) {
        try {
            // Get user and their groups
            User user = authService.getUserFromAuth(authHeader);
            
            // Get personal items with matching food name
            List<Fridge> fridgeItems = fridgeRepository.findByFood_NameIgnoreCaseAndUserId(foodName, userId);

            // Add items from user's groups
            List<GroupEntity> userGroups = groupRepository.findAllByMembersContainingOrAdminId(userId, userId);
            for (GroupEntity group : userGroups) {
                List<Fridge> groupItems = fridgeRepository.findByFood_NameIgnoreCaseAndGroupId(foodName, group.getId());
                fridgeItems.addAll(groupItems);
            }

            if (fridgeItems.isEmpty()) {
                throw new RuntimeException("Fridge item not found");
            }

            List<FridgeGetAllResponse.FridgeDto> fridgeDtos = fridgeItems.stream()
                    .map(fridge -> FridgeGetAllResponse.FridgeDto.builder()
                            .id(fridge.getId())
                            .startDate(fridge.getStartDate())
                            .expiredDate(fridge.getExpiredDate())
                            .quantity(fridge.getQuantity())
                            .note(fridge.getNote())
                            .foodName(fridge.getFood().getName())
                            .foodId(fridge.getFood().getId())
                            .groupId(fridge.getGroupId())
                            .build())
                    .collect(Collectors.toList());

            return FridgeGetAllResponse.builder()
                    .resultCode("00166")
                    .resultMessage(FridgeGetAllResponse.ResultMessage.builder()
                            .en("Get fridge items successfully")
                            .vn("Lấy các mục tủ lạnh thành công")
                            .build())
                    .fridgeItems(fridgeDtos)
                    .build();

        } catch (RuntimeException e) {
            return FridgeGetAllResponse.builder()
                    .resultCode("1999")
                    .resultMessage(FridgeGetAllResponse.ResultMessage.builder()
                            .en("System error: " + e.getMessage())
                            .vn("Lỗi hệ thống: " + e.getMessage())
                            .build())
                    .build();
        }
    }
}
