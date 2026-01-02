package com.example.smartShopping.controller;

import com.example.smartShopping.service.FCMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/test/fcm")
@RequiredArgsConstructor
public class FCMTestController {

    private final FCMService fcmService;

    /**
     * Test endpoint để gửi FCM notification
     * POST /api/test/fcm/send
     * Body: {
     *   "fcmToken": "your-device-fcm-token",
     *   "title": "Test Title",
     *   "body": "Test Body"
     * }
     */
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> testSendNotification(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String fcmToken = request.get("fcmToken");
            String title = request.getOrDefault("title", "Test Notification");
            String body = request.getOrDefault("body", "This is a test notification from SmartShopping API");
            
            if (fcmToken == null || fcmToken.isEmpty()) {
                response.put("success", false);
                response.put("message", "fcmToken is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            log.info("Testing FCM with token: {}", fcmToken);
            
            // Thêm test data
            Map<String, String> data = new HashMap<>();
            data.put("testKey", "testValue");
            data.put("timestamp", String.valueOf(System.currentTimeMillis()));
            
            // Gửi notification
            fcmService.sendNotification(fcmToken, title, body, data);
            
            response.put("success", true);
            response.put("message", "FCM notification sent successfully");
            response.put("fcmToken", fcmToken);
            response.put("title", title);
            response.put("body", body);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("FCM test failed", e);
            response.put("success", false);
            response.put("message", "Failed to send FCM notification: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Test endpoint để kiểm tra Firebase setup
     * GET /api/test/fcm/status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkFCMStatus() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Kiểm tra xem Firebase đã được initialize chưa
            com.google.firebase.FirebaseApp app = com.google.firebase.FirebaseApp.getInstance();
            
            response.put("initialized", true);
            response.put("appName", app.getName());
            response.put("projectId", app.getOptions().getProjectId());
            response.put("message", "Firebase is initialized and ready");
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalStateException e) {
            response.put("initialized", false);
            response.put("message", "Firebase is not initialized");
            response.put("error", e.getMessage());
            response.put("hint", "Check if firebase-service-account.json exists in resources folder");
            
            return ResponseEntity.status(503).body(response);
        } catch (Exception e) {
            log.error("Error checking FCM status", e);
            response.put("initialized", false);
            response.put("message", "Error checking Firebase status");
            response.put("error", e.getMessage());
            
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Test với dummy token (sẽ fail nhưng test được flow)
     * GET /api/test/fcm/dummy
     */
    @GetMapping("/dummy")
    public ResponseEntity<Map<String, Object>> testWithDummyToken() {
        Map<String, Object> response = new HashMap<>();
        
        String dummyToken = "dummy-fcm-token-for-testing-12345";
        
        try {
            log.info("Testing FCM with dummy token (expected to fail)");
            
            fcmService.sendNotification(
                dummyToken, 
                "Dummy Test", 
                "This should fail because token is invalid",
                Map.of("test", "dummy")
            );
            
            response.put("success", true);
            response.put("message", "Unexpected success - dummy token should fail!");
            
        } catch (Exception e) {
            // Expected to fail
            response.put("success", false);
            response.put("message", "Test completed (expected to fail with invalid token)");
            response.put("error", e.getMessage());
            response.put("status", "FCM service is working - tried to send but token is invalid");
            
            return ResponseEntity.ok(response); // Return 200 vì đây là expected behavior
        }
        
        return ResponseEntity.ok(response);
    }
}
