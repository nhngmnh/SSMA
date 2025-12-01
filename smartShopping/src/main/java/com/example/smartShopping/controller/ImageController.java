package com.example.smartShopping.controller;

import com.example.smartShopping.configuration.JwtTokenProvider;
import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.service.ImageStorageService;
import com.example.smartShopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageStorageService imageStorageService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadImage(
            @RequestPart("image") MultipartFile image,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long userId = extractUserId(authHeader);
            String url = imageStorageService.uploadImage(image, userId);
            return ResponseEntity.ok(
                    ApiResponse.<Map<String, String>>builder()
                            .success(true)
                            .code(200)
                            .message("Image uploaded successfully")
                            .data(Map.of("imageUrl", url))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Map<String, String>>builder()
                            .success(false)
                            .code(400)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PostMapping("/url")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadImageByUrl(
            @RequestParam("imageUrl") String imageUrl,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            Long userId = extractUserId(authHeader);
            String url = imageStorageService.uploadImageFromUrl(imageUrl, userId);
            return ResponseEntity.ok(
                    ApiResponse.<Map<String, String>>builder()
                            .success(true)
                            .code(200)
                            .message("Image uploaded successfully")
                            .data(Map.of("imageUrl", url))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Map<String, String>>builder()
                            .success(false)
                            .code(400)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    private Long extractUserId(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailFromToken(token);
        return userService.getUserIdByEmail(email);
    }
}

