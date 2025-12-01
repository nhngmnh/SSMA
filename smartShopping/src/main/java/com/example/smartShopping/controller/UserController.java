package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.*;
import com.example.smartShopping.dto.response.*;

import com.example.smartShopping.configuration.*;
import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.service.AuthService;
import com.example.smartShopping.service.UserService;
import com.example.smartShopping.service.GroupService;

import com.example.smartShopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Map.entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final GroupService groupService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // ===================== REGISTER =====================
    @PostMapping()
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest req) {
        System.out.println("=====> Register request received: " + req.getEmail());

        RegisterResponse response = authService.register(req);

        return ResponseEntity.ok(
                ApiResponse.<RegisterResponse>builder()
                        .success(true)
                        .code(201)
                        .message("User registered successfully")
                        .data(response)
                        .build()
        );
    }
    // ===================== GET INFOR USER =====================
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            Long userId = userService.getUserIdByEmail(email);

            // Lấy thông tin user
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getUserId());
            userMap.put("email", user.getEmail());
            userMap.put("password", ""); // không trả password thật
            userMap.put("username", user.getUsername());
            userMap.put("name", user.getName());
            userMap.put("type", "user");
            userMap.put("language", user.getLanguage() != null ? user.getLanguage() : "en");
            userMap.put("gender", user.getGender());
            userMap.put("countryCode", "US");
            userMap.put("timezone", user.getTimezone() != null ? user.getTimezone() : 7);
            userMap.put("birthDate", user.getDateOfBirth());
            userMap.put("photoUrl", user.getAvatarUrl());
            userMap.put("isActivated", user.getIsActive() != null ? user.getIsActive() : true);
            userMap.put("isVerified", user.getIsVerified() != null ? user.getIsVerified() : false);
            userMap.put("deviceId", user.getDeviceId());
            userMap.put("createdAt", user.getCreatedAt());
            userMap.put("updatedAt", user.getUpdatedAt());

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", Map.of(
                    "en", "The user information has gotten successfully.",
                    "vn", "Thông tin người dùng đã được lấy thành công."
            ));
            response.put("resultCode", "00089");
            response.put("user", userMap);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("resultMessage", Map.of("en", "Failed", "vn", "Thất bại"));
            error.put("resultCode", "99999");
            return ResponseEntity.status(500).body(error);
        }
    }

    // ===================== DELETE USER =====================
    @DeleteMapping("/")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestHeader("Authorization") String authHeader) {
        try {
            // Lấy token từ header
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);

            // Lấy user hiện tại
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Xóa user
            userRepository.delete(user);

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", Map.of(
                    "en", "Your account was deleted successfully.",
                    "vn", "Tài khoản của bạn đã bị xóa thành công."
            ));
            response.put("resultCode", "00092");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("resultMessage", Map.of("en", "Failed", "vn", "Thất bại"));
            error.put("resultCode", "99999");
            return ResponseEntity.status(500).body(error);
        }
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
    // ===================== CHANGE PASSWORD =====================
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(
            @ModelAttribute ChangePasswordRequest request, // hỗ trợ x-www-form-urlencoded
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            // Lấy token từ Header
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);

            // Gọi service đổi mật khẩu
            userService.changePassword(request, email);

            // Message đa ngôn ngữ
            Map<String, String> messages = new HashMap<>();
            messages.put("en", "Your password was changed successfully.");
            messages.put("vn", "Mật khẩu của bạn đã được thay đổi thành công.");

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", messages);
            response.put("resultCode", "00076");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> messages = new HashMap<>();
            messages.put("en", "The old password is incorrect.");
            messages.put("vn", "Mật khẩu cũ không chính xác.");

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", messages);
            response.put("resultCode", "00077");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    // ===================== EDIT USER + UPLOAD IMAGE =====================
    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> editUser(
            @RequestPart(value = "username", required = false) String username,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);

            // ✅ Upload ảnh nếu có
            String photoUrl = null;
            if (image != null && !image.isEmpty()) {
                photoUrl = userService.uploadAvatar(email, image); // viết trong service
            }

            // ✅ Cập nhật user
            userService.updateUserProfile(email, username, photoUrl);

            Map<String, String> messages = Map.of(
                    "en", "Your profile information was changed successfully.",
                    "vn", "Thông tin hồ sơ của bạn đã được thay đổi thành công."
            );

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", messages);
            response.put("resultCode", "00086");
            response.put("photoUrl", photoUrl);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", e.getMessage())
            );
        }
    }
    // ===================== SAVE NOTIFICATION TOKEN =====================
    @PutMapping("/token")
    public ResponseEntity<Map<String, Object>> saveNotificationToken(
            @RequestParam("token") String notificationToken,
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailFromToken(jwt);

        userService.saveUserNotificationToken(email, notificationToken);

        Map<String, String> messages = Map.of(
                "en", "Your notification token was saved successfully.",
                "vn", "Token thông báo của bạn đã được lưu thành công."
        );

        Map<String, Object> response = new HashMap<>();
        response.put("resultMessage", messages);
        response.put("resultCode", "00092");

        return ResponseEntity.ok(response);
    }


    // ===================== CREATE GROUP =====================
    @PostMapping("/group")
    public ResponseEntity<Map<String, Object>> createGroup(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailFromToken(token);

        Long adminId = userService.getUserIdByEmail(email);

        groupService.createGroup(adminId);

        Map<String, Object> response = new HashMap<>();
        response.put("resultMessage", Map.of(
                "en", "Your group has been created successfully",
                "vn", "Tạo nhóm thành công"
        ));
        response.put("resultCode", "00095");
        response.put("adminId", adminId);

        return ResponseEntity.ok(response);
    }

    // ===================== Get MEMBER GROUP =====================
    @GetMapping("/group")
    public ResponseEntity<Map<String, Object>> getGroup(@RequestHeader("Authorization") String authHeader) {
        try {
            // Lấy token từ header
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            Long userId = userService.getUserIdByEmail(email);

            // Lấy group mà user là admin
            GroupEntity group = groupService.getGroupByAdmin(userId);

            // Lấy thông tin members đầy đủ
            List<Map<String, Object>> members = group.getMembers().stream()
                    .map(id -> userRepository.findById(id)
                            .map(user -> {
                                Map<String, Object> memberMap = new HashMap<>();
                                memberMap.put("id", user.getUserId());
                                memberMap.put("email", user.getEmail());
                                memberMap.put("username", user.getUsername());
                                memberMap.put("name", user.getName());
                                memberMap.put("type", "user");
                                memberMap.put("language", user.getLanguage() != null ? user.getLanguage() : "en");
                                memberMap.put("gender", user.getGender()); // null nếu chưa có
                                memberMap.put("countryCode", "US"); // mặc định
                                memberMap.put("timezone", user.getTimezone() != null ? user.getTimezone() : 7);
                                memberMap.put("birthDate", user.getDateOfBirth()); // null nếu chưa có
                                memberMap.put("photoUrl", user.getAvatarUrl());
                                memberMap.put("isActivated", user.getIsActive() != null ? user.getIsActive() : true);
                                memberMap.put("isVerified", user.getIsVerified() != null ? user.getIsVerified() : false);
                                memberMap.put("deviceId", user.getDeviceId());
                                memberMap.put("createdAt", user.getCreatedAt());
                                memberMap.put("updatedAt", user.getUpdatedAt());
                                return memberMap;
                            })
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", Map.of("en", "Successfully", "vn", "Thành công"));
            response.put("groupAdmin", group.getAdminId());
            response.put("members", members);
            response.put("resultCode", "00098");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("resultMessage", Map.of("en", "Failed", "vn", "Thất bại"));
            error.put("resultCode", "99999");
            return ResponseEntity.status(500).body(error);
        }
    }
    // ===================== ADD MEMBER GROUP =====================
    @PostMapping("/group/add")
    public ResponseEntity<Map<String, Object>> addMemberToGroup(
            @RequestParam("username") String username,
            @RequestHeader("Authorization") String authHeader) {

        System.out.println("POST /group/add called with username=" + username);

        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            Long adminId = userService.getUserIdByEmail(email);

            // Lấy group của admin
            GroupEntity group = groupService.getGroupByAdmin(adminId);

            // Lấy userId của member cần thêm
            Long memberId = userService.getUserIdByUsername(username);

            // Thêm member vào group
            groupService.addMember(group.getId(), memberId);

            // Build response
            Map<String, Object> response = new HashMap<>();
            Map<String, String> resultMessage = new HashMap<>();
            resultMessage.put("en", "User added to the group successfully");
            resultMessage.put("vn", "Người dùng thêm vào nhóm thành công");

            response.put("resultMessage", resultMessage);
            response.put("resultCode", "00102");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            e.printStackTrace(); // in stack trace
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage()); // hiển thị lỗi thực
            error.put("code", 1999);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    // ===================== DELETE MEMBER GROUP =====================
    @DeleteMapping("/group")
    public ResponseEntity<Map<String, Object>> removeMemberFromGroup(
            @RequestParam("username") String username,
            @RequestHeader("Authorization") String authHeader) {

        try {
            // Lấy token từ header
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            Long adminId = userService.getUserIdByEmail(email);

            // Lấy group của admin
            GroupEntity group = groupService.getGroupByAdmin(adminId);

            // Lấy userId của member cần xóa
            Long memberId = userService.getUserIdByUsername(username);

            // Xóa member khỏi group
            groupService.removeMember(group.getId(), memberId);

            // Build response
            Map<String, Object> response = new HashMap<>();
            Map<String, String> resultMessage = new HashMap<>();
            resultMessage.put("en", "User removed from the group successfully");
            resultMessage.put("vn", "Xóa thành công");

            response.put("resultMessage", resultMessage);
            response.put("resultCode", "00106");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            Map<String, String> resultMessage = new HashMap<>();
            resultMessage.put("en", e.getMessage());
            resultMessage.put("vn", "Xóa người dùng thất bại");

            error.put("resultMessage", resultMessage);
            error.put("resultCode", "00107");

            return ResponseEntity.status(400).body(error);
        }
    }


}
