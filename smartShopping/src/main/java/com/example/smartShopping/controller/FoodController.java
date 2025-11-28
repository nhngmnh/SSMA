package com.example.smartShopping.controller;



import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.response.FoodResponse;
import com.example.smartShopping.service.FoodService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public FoodResponse createFood(
            @ModelAttribute FoodRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long userId = extractUserIdFromToken(authHeader);
            return foodService.createFood(req, userId);
        } catch (Exception e) {
            e.printStackTrace(); // xem stacktrace ở console
            return FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build();
        }
    }


    private Long extractUserIdFromToken(String header) {
        // TODO: decode JWT
        return 2L;
    }
}

