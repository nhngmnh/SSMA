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
import com.example.smartShopping.repository.FoodRepository;
import com.example.smartShopping.repository.FridgeRepository;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.service.FridgeService;
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

    @Override
    public FridgeCreateResponse createFridge(CreateFridgeRequest request) {
        try {
            User user = getCurrentUser();

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
    public FridgeUpdateResponse updateFridge(UpdateFridgeRequest request) {
        try {
            User user = getCurrentUser();

            Fridge fridge = fridgeRepository.findByIdAndUserId(request.getItemId(), user.getId())
                    .orElseThrow(() -> new RuntimeException("Fridge item not found"));

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
    public FridgeDeleteResponse deleteFridgeItem(String foodName) {
        try {
            User user = getCurrentUser();

            int deletedCount = fridgeRepository.deleteByUserIdAndFoodName(user.getId(), foodName);

            if (deletedCount == 0) {
                throw new RuntimeException("Fridge item not found: " + foodName);
            }

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
    public FridgeGetAllResponse getAllFridgeItems() {
        try {
            User user = getCurrentUser();

            List<Fridge> fridgeList = fridgeRepository.findByUserId(user.getId());

            List<FridgeGetAllResponse.FridgeDto> fridgeDtos = fridgeList.stream()
                    .map(fridge -> FridgeGetAllResponse.FridgeDto.builder()
                            .id(fridge.getId())
                            .startDate(fridge.getStartDate())
                            .expiredDate(fridge.getExpiredDate())
                            .quantity(fridge.getQuantity())
                            .note(fridge.getNote())
                            .foodName(fridge.getFood().getName())
                            .foodId(fridge.getFood().getId())
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
    public FridgeGetAllResponse getFridgeItemsByFoodName(String foodName) {
        try {
            User user = getCurrentUser();

            List<Fridge> fridgeItems = fridgeRepository.findByFood_NameIgnoreCaseAndUserId(foodName, user.getId());

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

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
