package com.example.smartShopping.controller;

import com.example.smartShopping.service.FCMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final FCMService fcmService;

    /**
     * Test endpoint để gửi FCM notification
     * POST /test/fcm
     * Body: {
     *   "fcmToken": "device-fcm-token",
     *   "title": "Test Notification",
     *   "body": "This is a test message"
     * }
     */
    @PostMapping("/fcm")
    public ResponseEntity<Map<String, Object>> testFCM(@RequestBody TestFCMRequest request) {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        
        try {
            // Validate input
            if (request.getFcmToken() == null || request.getFcmToken().isEmpty()) {
                response.put("resultCode", "40001");
                response.put("resultMessage", Map.of(
                    "en", "FCM token is required",
                    "vn", "FCM token là bắt buộc"
                ));
                return ResponseEntity.badRequest().body(response);
            }

            // Prepare data payload
            Map<String, String> data = new HashMap<>();
            data.put("type", "test");
            data.put("timestamp", String.valueOf(System.currentTimeMillis()));

            // Send notification
            fcmService.sendNotification(
                request.getFcmToken(),
                request.getTitle() != null ? request.getTitle() : "Test Notification",
                request.getBody() != null ? request.getBody() : "This is a test FCM notification from SmartShopping app",
                data
            );

            log.info("Test FCM notification sent to token: {}", request.getFcmToken());

            response.put("resultCode", "00000");
            response.put("resultMessage", Map.of(
                "en", "FCM notification sent successfully",
                "vn", "Đã gửi thông báo FCM thành công"
            ));
            response.put("data", Map.of(
                "fcmToken", request.getFcmToken(),
                "title", request.getTitle() != null ? request.getTitle() : "Test Notification",
                "body", request.getBody() != null ? request.getBody() : "This is a test FCM notification"
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error sending test FCM notification", e);
            response.put("resultCode", "50000");
            response.put("resultMessage", Map.of(
                "en", "Failed to send FCM notification: " + e.getMessage(),
                "vn", "Không thể gửi thông báo FCM: " + e.getMessage()
            ));
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Test endpoint để gửi FCM notification đến nhiều device
     */
    @PostMapping("/fcm/multicast")
    public ResponseEntity<Map<String, Object>> testMulticastFCM(@RequestBody TestMulticastFCMRequest request) {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        
        try {
            if (request.getFcmTokens() == null || request.getFcmTokens().isEmpty()) {
                response.put("resultCode", "40001");
                response.put("resultMessage", Map.of(
                    "en", "FCM tokens are required",
                    "vn", "FCM tokens là bắt buộc"
                ));
                return ResponseEntity.badRequest().body(response);
            }

            Map<String, String> data = new HashMap<>();
            data.put("type", "test_multicast");
            data.put("timestamp", String.valueOf(System.currentTimeMillis()));

            int successCount = fcmService.sendMulticastNotification(
                request.getFcmTokens(),
                request.getTitle() != null ? request.getTitle() : "Test Multicast",
                request.getBody() != null ? request.getBody() : "This is a multicast test",
                data
            );

            log.info("Test multicast FCM sent to {} devices, {} successful", 
                     request.getFcmTokens().size(), successCount);

            response.put("resultCode", "00000");
            response.put("resultMessage", Map.of(
                "en", "Multicast FCM notification sent",
                "vn", "Đã gửi thông báo FCM đến nhiều thiết bị"
            ));
            response.put("data", Map.of(
                "totalDevices", request.getFcmTokens().size(),
                "successCount", successCount,
                "failureCount", request.getFcmTokens().size() - successCount
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error sending multicast FCM notification", e);
            response.put("resultCode", "50000");
            response.put("resultMessage", Map.of(
                "en", "Failed to send multicast FCM: " + e.getMessage(),
                "vn", "Không thể gửi thông báo FCM: " + e.getMessage()
            ));
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Health check cho Firebase
     */
    @GetMapping("/fcm/health")
    public ResponseEntity<Map<String, Object>> checkFirebaseHealth() {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        
        try {
            // Kiểm tra Firebase đã khởi tạo chưa
            com.google.firebase.FirebaseApp app = com.google.firebase.FirebaseApp.getInstance();
            
            response.put("resultCode", "00000");
            response.put("resultMessage", Map.of(
                "en", "Firebase is initialized",
                "vn", "Firebase đã được khởi tạo"
            ));
            response.put("data", Map.of(
                "firebaseAppName", app.getName(),
                "projectId", app.getOptions().getProjectId()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalStateException e) {
            response.put("resultCode", "50001");
            response.put("resultMessage", Map.of(
                "en", "Firebase not initialized",
                "vn", "Firebase chưa được khởi tạo"
            ));
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // DTO classes
    @lombok.Data
    public static class TestFCMRequest {
        private String fcmToken;
        private String title;
        private String body;
    }

    @lombok.Data
    public static class TestMulticastFCMRequest {
        private java.util.List<String> fcmTokens;
        private String title;
        private String body;
    }
}
