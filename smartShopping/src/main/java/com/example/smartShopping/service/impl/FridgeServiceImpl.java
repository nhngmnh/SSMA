package com.example.smartShopping.service.impl;

import com.example.smartShopping.dto.request.CreateFridgeRequest;
import com.example.smartShopping.dto.request.UpdateFridgeRequest;
import com.example.smartShopping.dto.response.FridgeResponse;
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
    public FridgeResponse createFridge(CreateFridgeRequest request) {

        // 1. Lấy user hiện tại từ Security
        User user = getCurrentUser();

        // 2. Tìm Food theo name
        Food food = foodRepository.findByNameIgnoreCase(request.getFoodName())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        // 3. Tính ngày hết hạn
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expiredDate = startDate.plusMinutes(request.getUseWithin());

        // 4. Tạo entity
        Fridge fridge = Fridge.builder()
                .startDate(startDate)
                .expiredDate(expiredDate)
                .quantity(request.getQuantity())
                .food(food)
                .user(user)
                .build();

        // 5. Lưu DB
        fridgeRepository.save(fridge);

        // 6. Trả DTO
        return FridgeResponse.fromEntity(fridge);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public FridgeResponse updateFridge(UpdateFridgeRequest request) {

        User user = getCurrentUser();

        // 1️⃣ Lấy item trong fridge của đúng user
        Fridge fridge = fridgeRepository
                .findByIdAndUserId(request.getItemId(), user.getId())
                .orElseThrow(() -> new RuntimeException("Fridge item not found"));

        // 2️⃣ Update note
        if (request.getNewNote() != null) {
            fridge.setNote(request.getNewNote());
        }

        // 3️⃣ Update quantity
        if (request.getNewQuantity() != null) {
            fridge.setQuantity(request.getNewQuantity());
        }

        // 4️⃣ Update food
        if (request.getNewFoodName() != null) {
            Food food = foodRepository
                    .findByNameIgnoreCase(request.getNewFoodName())
                    .orElseThrow(() -> new RuntimeException("Food not found"));
            fridge.setFood(food);
        }

        // 5️⃣ Update expiredDate theo newUseWithin
        if (request.getNewUseWithin() != null) {
            fridge.setExpiredDate(
                    fridge.getStartDate().plusMinutes(request.getNewUseWithin())
            );
        }

        // 6️⃣ Save
        fridgeRepository.save(fridge);

        return FridgeResponse.fromEntity(fridge);
    }
    @Override
    public void deleteFridgeItem(String foodName) {

        User user = getCurrentUser();

        int deletedCount = fridgeRepository.deleteByUserIdAndFoodName(user.getId(), foodName);

        if (deletedCount == 0) {
            throw new RuntimeException("Fridge item not found: " + foodName);
        }
    }

    @Override
    public List<FridgeResponse> getAllFridgeItems() {
        User user = getCurrentUser();

        List<Fridge> fridgeList = fridgeRepository.findByUserId(user.getId());

        return fridgeList.stream()
                .map(FridgeResponse::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<FridgeResponse> getFridgeItemsByFoodName(String foodName) {
        User user = getCurrentUser();
        List<Fridge> fridgeItems = fridgeRepository
                .findByFood_NameIgnoreCaseAndUserId(foodName, user.getId());

        if (fridgeItems.isEmpty()) {
            throw new RuntimeException("Fridge item not found");
        }

        return fridgeItems.stream()
                .map(FridgeResponse::fromEntity)
                .collect(Collectors.toList());
    }




}
