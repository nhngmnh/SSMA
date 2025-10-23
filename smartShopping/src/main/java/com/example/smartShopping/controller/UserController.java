package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.*;
import com.example.smartShopping.dto.response.*;

import com.example.smartShopping.configuration.*;
import com.example.smartShopping.service.AuthService;
import com.example.smartShopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // ===================== REGISTER =====================
    @PostMapping("/")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest req) {
        System.out.println("=====> Register request received: " + req.getEmail());

        RegisterResponse response = authService.register(req); // ✅ trả về RegisterResponse

        return ResponseEntity.ok(
                ApiResponse.<RegisterResponse>builder()
                        .success(true)
                        .code(201)
                        .message("User registered successfully")
                        .data(response) // ✅ truyền đúng kiểu
                        .build()
        );
    }




    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println("===> Login request: " + request.getEmail());
        LoginResponse response = authService.login(request);
        System.out.println("===> Login done, returning response");

        return ResponseEntity.ok(response);
    }


    // ===================== LOGOUT =====================
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout() {
        authService.logout();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .code(200)
                        .message("Logout successful")
                        .build()
        );
    }

    // ===================== REFRESH TOKEN =====================
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody RefreshTokenRequest req) {
        try {
            String email = jwtTokenProvider.getEmailFromToken(req.getRefreshToken());

            String accessToken = jwtTokenProvider.generateAccessToken(email);
            String refreshToken = jwtTokenProvider.generateRefreshToken(email);

            Map<String, String> messages = new HashMap<>();
            messages.put("en", "The token is refreshed successfully.");
            messages.put("vn", "Token đã được làm mới thành công.");

            RefreshTokenResponse newTokens = new RefreshTokenResponse(messages, "00065", accessToken, refreshToken);

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .code(200)
                            .message("Token refreshed")
                            .data(newTokens)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace(); // xem lỗi thực sự
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .success(false)
                            .code(1999)
                            .message("Lỗi hệ thống")
                            .build()
            );
        }
    }




    // ===================== SEND VERIFICATION CODE =====================
    @PostMapping("/send-verification-code")
    public ResponseEntity<ApiResponse<?>> sendCode(@RequestParam("email") String email) {
        Map<String, String> messages = Map.of(
                "en", "The code is sent to your email successfully.",
                "vn", "Mã đã được gửi đến email của bạn thành công."
        );

        String confirmToken = jwtTokenProvider.generateVerificationToken(email, "1234");

        Map<String, Object> data = Map.of(
                "resultMessage", messages,
                "resultCode", "00048",
                "confirmToken", confirmToken
        );

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .code(200)
                        .message("Code sent")
                        .data(data)
                        .build()
        );
    }

    // ===================== VERIFY EMAIL =====================

    @PostMapping("/verify-email")
    public ResponseEntity<VerifyEmailResponse> verifyEmail(
            @RequestParam("code") String code,
            @RequestParam("token") String token) {

        // Gọi service để xác thực code + token
        boolean verified = authService.verifyEmail(code, token);
        if (!verified) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("code=" + code + ", token=" + token);

        // Tạo message đa ngôn ngữ
        Map<String, String> messages = new HashMap<>();
        messages.put("en", "Your email address was verified successfully.");
        messages.put("vn", "Địa chỉ email của bạn đã được xác minh thành công.");

        // Tạo access token & refresh token mới
        String accessToken = authService.generateAccessTokenFromEmail(authService.getEmailFromToken(token));
        String refreshToken = authService.generateRefreshTokenFromEmail(authService.getEmailFromToken(token));

        VerifyEmailResponse response = new VerifyEmailResponse(messages, "00058", accessToken, refreshToken);
        return ResponseEntity.ok(response);
    }
}
