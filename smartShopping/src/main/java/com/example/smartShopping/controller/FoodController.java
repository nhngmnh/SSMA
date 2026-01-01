package com.example.smartShopping.controller;



import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.request.UpdateFoodRequest;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.service.FoodService;
import com.example.smartShopping.configuration.JwtTokenProvider;
import com.example.smartShopping.service.UnitService;
import com.example.smartShopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final UnitService unitService;
    private final CategoryService categoryService; // thêm service unit
    private final JwtTokenProvider jwtTokenProvider;

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
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = header.substring(7); // bỏ "Bearer "

        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenProvider.getSecret()) // lấy secret từ provider
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
    }

    @PutMapping
    public FoodUpdateResponse updateFood(
            @ModelAttribute UpdateFoodRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long userId = extractUserIdFromToken(authHeader);
            return foodService.updateFood(req, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return FoodUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodUpdateResponse.ResultMessage(
                            "System error", "Lỗi hệ thống"
                    ))
                    .build();
        }
    }
    @DeleteMapping
    public FoodDeleteResponse deleteFood(
            @RequestParam String name,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long UserId = extractUserIdFromToken(authHeader);
            return foodService.deleteFood(name, UserId);
        } catch (Exception e) {
            e.printStackTrace();
            return FoodDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(
                            FoodDeleteResponse.ResultMessage.builder()
                                    .vn("Lỗi hệ thống")
                                    .en("System error")
                                    .build()
                    )
                    .build();
        }
    }
    @GetMapping
    public FoodResponse getAllFoods() {
        try {
            return foodService.getAllFoods();
        } catch (Exception e) {
            e.printStackTrace();
            return FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build();
        }
    }
    @GetMapping("/unit")
    public ResponseEntity<ApiResponse> getAllUnitsFood(
            @RequestParam(required = false) String unitName) {
        ApiResponse response = unitService.getAllUnits(unitName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getAllCategoriesFood() {
        CategoryResponse data = categoryService.getAllCategoriesFood();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Lấy danh sách category thành công")
                .code(129) // resultCode: 00129
                .data(data)
                .build());
    }

}

