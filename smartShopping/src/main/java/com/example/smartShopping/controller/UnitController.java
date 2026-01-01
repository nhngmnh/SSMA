package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.service.UnitService;
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

    @PostMapping
    public Object createUnit(@RequestParam String unitName) {
        try {
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
            @RequestParam(required = false) String unitName) {
        try {
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
            @RequestParam String newName) {
        try {
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
    public Object deleteUnit(@RequestParam String unitName) {
        try {
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
}
