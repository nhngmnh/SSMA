package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.UnitService;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.configuration.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public Object createUnit(@RequestParam String unitName, @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            UnitRequest request = new UnitRequest();
            request.setUnitName(unitName);
            return ResponseEntity.ok(unitService.createUnit(request));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    @GetMapping
    public Object getAllUnits(
            @RequestParam(required = false) String unitName,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            return ResponseEntity.ok(unitService.getAllUnits(unitName));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    @PutMapping
    public Object updateUnit(
            @RequestParam String oldName,
            @RequestParam String newName,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            return ResponseEntity.ok(unitService.updateUnitName(oldName, newName));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    @DeleteMapping
    public Object deleteUnit(@RequestParam String unitName, @RequestHeader("Authorization") String authHeader) {
        try {
            // Check if user is admin
            if (!isAdmin(authHeader)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "Access denied - Admin permission required");
                resultMessage.put("vn", "Truy cập bị từ chối - Yêu cầu quyền Admin");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "40301");
                return ResponseEntity.status(403).body(errorResponse);
            }
            
            return ResponseEntity.ok(unitService.deleteUnitByName(unitName));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
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
