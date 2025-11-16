package com.example.smartShopping.controller;

import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllLogs() {
        ApiResponse response = logService.getAllLogs();
        return ResponseEntity.ok(response);
    }
}
