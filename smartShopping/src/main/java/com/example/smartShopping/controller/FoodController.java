package com.example.smartShopping.controller;



import com.example.smartShopping.dto.request.FoodRequest;
import com.example.smartShopping.dto.request.UpdateFoodRequest;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.FoodService;
import com.example.smartShopping.configuration.JwtTokenProvider;
import com.example.smartShopping.service.UnitService;
import com.example.smartShopping.service.CategoryService;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final UnitService unitService;
    private final CategoryService categoryService; // thêm service unit
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @PostMapping
    public Object createFood(
            @ModelAttribute FoodRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                java.util.Map<String, Object> errorResponse = new java.util.LinkedHashMap<>();
                java.util.Map<String, String> resultMessage = new java.util.LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            Long userId = jwtUtils.extractUserIdFromHeader(authHeader);
            return ResponseEntity.ok(foodService.createFood(req, userId));
        } catch (Exception e) {
            e.printStackTrace(); // xem stacktrace ở console
            return ResponseEntity.status(500).body(FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build());
        }
    }



    @PutMapping
    public Object updateFood(
            @ModelAttribute UpdateFoodRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long userId = jwtUtils.extractUserIdFromHeader(authHeader);
            return ResponseEntity.ok(foodService.updateFood(req, userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(FoodUpdateResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodUpdateResponse.ResultMessage(
                            "System error", "Lỗi hệ thống"
                    ))
                    .build());
        }
    }
    @DeleteMapping
    public Object deleteFood(
            @RequestParam String name,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long UserId = jwtUtils.extractUserIdFromHeader(authHeader);
            return ResponseEntity.ok(foodService.deleteFood(name, UserId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(FoodDeleteResponse.builder()
                    .resultCode("1999")
                    .resultMessage(
                            FoodDeleteResponse.ResultMessage.builder()
                                    .vn("Lỗi hệ thống")
                                    .en("System error")
                                    .build()
                    )
                    .build());
        }
    }
    @GetMapping
    public Object getAllFoods() {
        try {
            return ResponseEntity.ok(foodService.getAllFoods());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build());
        }
    }

    @GetMapping("/{id}")
    public Object getFoodById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(foodService.getFoodById(id));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(FoodResponse.builder()
                    .resultCode("1404")
                    .resultMessage(new FoodResponse.ResultMessage("Không tìm thấy thực phẩm", "Food not found"))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build());
        }
    }

    @GetMapping("/search")
    public Object searchFoods(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(foodService.searchFoods(keyword));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build());
        }
    }

    @GetMapping("/group/{groupId}")
    public Object getFoodsByGroupId(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(foodService.getFoodsByGroupId(groupId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(FoodResponse.builder()
                    .resultCode("1999")
                    .resultMessage(new FoodResponse.ResultMessage("Lỗi hệ thống", "System error"))
                    .build());
        }
    }

    @GetMapping("/unit")
    public Object getAllUnitsFood(@RequestParam(required = false) String unitName) {
        try {
            UnitResponse response = unitService.getAllUnits(unitName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            java.util.Map<String, Object> errorResponse = new java.util.LinkedHashMap<>();
            java.util.Map<String, String> resultMessage = new java.util.LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/category")
    public Object getAllCategoriesFood() {
        try {
            CategoryResponse data = categoryService.getAllCategoriesFood();
            
            java.util.Map<String, Object> response = new java.util.LinkedHashMap<>();
            java.util.Map<String, String> resultMessage = new java.util.LinkedHashMap<>();
            resultMessage.put("en", "Categories retrieved successfully");
            resultMessage.put("vn", "Lấy danh sách category thành công");
            
            response.put("resultMessage", resultMessage);
            response.put("resultCode", "00129");
            response.put("categories", data.getCategories());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            java.util.Map<String, Object> errorResponse = new java.util.LinkedHashMap<>();
            java.util.Map<String, String> resultMessage = new java.util.LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    private boolean isAdmin(String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            User user = userRepository.findByEmail(email).orElse(null);
            return user != null && user.getIsAdmin() != null && user.getIsAdmin();
        } catch (Exception e) {
            return false;
        }
    }

}

