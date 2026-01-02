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
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    @PostMapping(consumes = {"application/json", "application/x-www-form-urlencoded"})
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
        log.info("Received request to register user: {}", req);
        RegisterResponse response = authService.register(req);
        return ResponseEntity.ok(response);
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Validate login
            User user = authService.validateLogin(request);

            // Generate access + refresh token
            String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail());
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

            // Lưu token vào bảng Token
            authService.saveOrUpdateToken(
                user.getId(), 
                request.getDeviceId(), 
                accessToken, 
                refreshToken, 
                request.getFcmToken()
            );

            // Build response trả FE
            LoginResponse response = LoginResponse.builder()
                    .resultCode("00047")
                    .resultMessage(new ResultMessage("You logged in successfully.", "Bạn đã đăng nhập thành công."))
                    .user(user)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            // Xử lý các lỗi cụ thể
            String errorMsg = e.getMessage();
            String resultCode;
            ResultMessage resultMessage;
            
            if (errorMsg.contains("Email không tồn tại")) {
                resultCode = "40001";
                resultMessage = new ResultMessage("Email does not exist", "Email không tồn tại");
            } else if (errorMsg.contains("Mật khẩu không đúng")) {
                resultCode = "40002";
                resultMessage = new ResultMessage("Incorrect password", "Mật khẩu không đúng");
            } else if (errorMsg.contains("Email chưa được xác thực")) {
                resultCode = "40003";
                resultMessage = new ResultMessage("Email not verified. Please check your email for verification code", "Email chưa được xác thực. Vui lòng kiểm tra email để lấy mã xác thực");
            } else if (errorMsg.contains("Tài khoản bị khóa")) {
                resultCode = "40004";
                resultMessage = new ResultMessage("Account is locked", "Tài khoản bị khóa");
            } else {
                resultCode = "1999";
                resultMessage = new ResultMessage("System error", "Lỗi hệ thống");
            }
            
            LoginResponse errorResponse = LoginResponse.builder()
                    .resultCode(resultCode)
                    .resultMessage(resultMessage)
                    .user(null)
                    .accessToken(null)
                    .refreshToken(null)
                    .build();
                    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }




    // ===================== LOGOUT =====================
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            @RequestParam String deviceId,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            Long userId = userService.getUserIdByEmail(email);
            
            authService.logout(userId, deviceId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", Map.of(
                "en", "Logged out successfully",
                "vn", "Đăng xuất thành công"
            ));
            response.put("resultCode", "00049");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Logout error", e);
            Map<String, Object> error = new HashMap<>();
            error.put("resultMessage", Map.of(
                "en", "Logout failed",
                "vn", "Đăng xuất thất bại"
            ));
            error.put("resultCode", "1999");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ===================== REFRESH TOKEN =====================
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest req) {
        try {
            // Validate refresh token từ Token table
            com.example.smartShopping.entity.Token tokenEntity = authService.getTokenRepository()
                    .findByRefreshTokenAndIsActive(req.getRefreshToken(), true)
                    .orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));
            
            String email = jwtTokenProvider.getEmailFromToken(req.getRefreshToken());
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Generate new tokens
            String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), email);
            String refreshToken = jwtTokenProvider.generateRefreshToken(email);

            // Update token trong database
            authService.saveOrUpdateToken(
                user.getId(), 
                req.getDeviceId(), 
                accessToken, 
                refreshToken, 
                tokenEntity.getFcmToken()
            );

            Map<String, String> messages = new HashMap<>();
            messages.put("en", "The token is refreshed successfully.");
            messages.put("vn", "Token đã được làm mới thành công.");

            RefreshTokenResponse newTokens = new RefreshTokenResponse(messages, "00065", accessToken, refreshToken);

            return ResponseEntity.ok(newTokens);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorMsg = new HashMap<>();
            errorMsg.put("en", "Invalid or expired refresh token");
            errorMsg.put("vn", "Refresh token không hợp lệ hoặc đã hết hạn");
            RefreshTokenResponse error = new RefreshTokenResponse(errorMsg, "40009", null, null);
            return ResponseEntity.status(401).body(error);
        }
    }




    // ===================== SEND VERIFICATION CODE =====================
    @PostMapping("/send-verification-code")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestParam("email") String email) {
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

        return ResponseEntity.ok(data);
    }

    // ===================== VERIFY EMAIL =====================

    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, Object>> verifyEmail(@RequestBody VerifyEmailRequest request) {
        try {
            // Gọi service để xác thực code + token
            boolean verified = authService.verifyEmail(request.getCode(), request.getToken());
            if (!verified) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("resultMessage", Map.of(
                    "en", "Invalid verification code",
                    "vn", "Mã xác thực không đúng"
                ));
                errorResponse.put("resultCode", "40013");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Tạo access token & refresh token mới
            String accessToken = authService.generateAccessTokenFromEmail(authService.getEmailFromToken(request.getToken()));
            String refreshToken = authService.generateRefreshTokenFromEmail(authService.getEmailFromToken(request.getToken()));

            Map<String, Object> response = new HashMap<>();
            response.put("resultMessage", Map.of(
                "en", "Email verified successfully. You can now login",
                "vn", "Xác thực email thành công. Bạn có thể đăng nhập ngay"
            ));
            response.put("resultCode", "00012");
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            
            // Xử lý các loại lỗi khác nhau
            if (e.getMessage().contains("hết hạn")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Verification code has expired",
                    "vn", "Mã xác thực đã hết hạn"
                ));
                errorResponse.put("resultCode", "40014");
            } else if (e.getMessage().contains("Không tìm thấy")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "User not found",
                    "vn", "Không tìm thấy người dùng"
                ));
                errorResponse.put("resultCode", "40015");
            } else {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Verification failed: " + e.getMessage(),
                    "vn", "Xác thực thất bại: " + e.getMessage()
                ));
                errorResponse.put("resultCode", "40016");
            }
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("resultMessage", Map.of(
                "en", "System error: " + e.getMessage(),
                "vn", "Lỗi hệ thống: " + e.getMessage()
            ));
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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

    // ===================== FORGOT PASSWORD =====================
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            Map<String, Object> response = authService.forgotPassword(request.getEmail());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            
            if (e.getMessage().contains("Email không tồn tại")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Email does not exist",
                    "vn", "Email không tồn tại"
                ));
                errorResponse.put("resultCode", "40005");
            } else if (e.getMessage().contains("chưa được xác thực")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Account not verified. Please verify your email first",
                    "vn", "Tài khoản chưa được xác thực. Vui lòng xác thực email trước"
                ));
                errorResponse.put("resultCode", "40006");
            } else {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Failed to send reset code: " + e.getMessage(),
                    "vn", "Không thể gửi mã đặt lại: " + e.getMessage()
                ));
                errorResponse.put("resultCode", "1999");
            }
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // ===================== RESET PASSWORD =====================
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            Map<String, Object> response = authService.resetPassword(
                request.getEmail(), 
                request.getResetCode(), 
                request.getNewPassword()
            );
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            
            if (e.getMessage().contains("Email không tồn tại")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Email does not exist",
                    "vn", "Email không tồn tại"
                ));
                errorResponse.put("resultCode", "40005");
            } else if (e.getMessage().contains("không đúng")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Invalid reset code",
                    "vn", "Mã đặt lại mật khẩu không đúng"
                ));
                errorResponse.put("resultCode", "40007");
            } else if (e.getMessage().contains("hết hạn")) {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Reset code has expired. Please request a new one",
                    "vn", "Mã đặt lại đã hết hạn. Vui lòng yêu cầu mã mới"
                ));
                errorResponse.put("resultCode", "40008");
            } else {
                errorResponse.put("resultMessage", Map.of(
                    "en", "Password reset failed: " + e.getMessage(),
                    "vn", "Đặt lại mật khẩu thất bại: " + e.getMessage()
                ));
                errorResponse.put("resultCode", "1999");
            }
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
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
