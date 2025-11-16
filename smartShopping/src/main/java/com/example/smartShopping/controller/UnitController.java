package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.UnitRequest;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUnit(@RequestParam String unitName) {
        UnitRequest request = new UnitRequest();
        request.setUnitName(unitName);
        return ResponseEntity.ok(unitService.createUnit(request));
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUnits(
            @RequestParam(required = false) String unitName) {
        ApiResponse response = unitService.getAllUnits(unitName);
        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<ApiResponse> updateUnit(
            @RequestParam String oldName,
            @RequestParam String newName) {

        ApiResponse response = unitService.updateUnitName(oldName, newName);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUnit(@RequestParam String unitName) {
        ApiResponse response = unitService.deleteUnitByName(unitName);
        return ResponseEntity.ok(response);
    }
}
