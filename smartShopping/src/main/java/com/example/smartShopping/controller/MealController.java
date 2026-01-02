package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.DeleteMealRequest;
import com.example.smartShopping.dto.request.MealRequest;
import com.example.smartShopping.dto.request.UpdateMealRequest;
import com.example.smartShopping.dto.response.MealDeleteResponse;
import com.example.smartShopping.dto.response.MealDetailResponse;
import com.example.smartShopping.dto.response.MealGetAllResponse;
import com.example.smartShopping.dto.response.MealResponse;
import com.example.smartShopping.dto.response.MealUpdateResponse;
import com.example.smartShopping.service.MealService;
import com.example.smartShopping.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(
            @RequestBody MealRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long userId = jwtUtils.extractUserIdFromHeader(authHeader);
            MealResponse response = mealService.createMeal(request, userId, authHeader);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    MealResponse.builder()
                            .resultCode("1999")
                            .resultMessage(MealResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }

    @PutMapping
    public ResponseEntity<MealUpdateResponse> updateMeal(@RequestBody UpdateMealRequest request) {
        try {
            MealUpdateResponse response = mealService.updateMeal(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    MealUpdateResponse.builder()
                            .resultCode("1999")
                            .resultMessage(MealUpdateResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<MealDeleteResponse> deleteMeal(@RequestBody DeleteMealRequest request) {
        try {
            MealDeleteResponse response = mealService.deleteMeal(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    MealDeleteResponse.builder()
                            .resultCode("1999")
                            .resultMessage(MealDeleteResponse.ResultMessage.builder()
                                    .en("System error")
                                    .vn("Lỗi hệ thống")
                                    .build())
                            .build()
            );
        }
    }

    @GetMapping
    public Object getAllMeals() {
        try {
            MealGetAllResponse response = mealService.getAllMeals();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public Object getMealById(@PathVariable Long id) {
        try {
            MealDetailResponse response = mealService.getMealById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
