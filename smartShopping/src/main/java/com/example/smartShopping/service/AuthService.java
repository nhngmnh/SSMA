package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.*;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.entity.Token;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.repository.TokenRepository;
import com.example.smartShopping.configuration.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtProvider;
    private final EmailService emailService;

    public RegisterResponse register(RegisterRequest req) {
        // Kiểm tra xem email đã tồn tại chưa
        User existingUser = userRepo.findByEmail(req.getEmail()).orElse(null);
        
        // Nếu user đã tồn tại VÀ đã verify -> không cho đăng ký lại
        if (existingUser != null && existingUser.getIsVerified()) {
            throw new RuntimeException("Email đã được đăng ký và xác thực");
        }
        
        // Nếu user đã tồn tại NHƯNG chưa verify -> cho phép gửi lại mã
        if (existingUser != null && !existingUser.getIsVerified()) {
            log.info("User exists but not verified. Resending verification code to: {}", req.getEmail());
            
            // Tạo verification code mới
            String verificationCode = String.valueOf(100000 + new Random().nextInt(900000));
            
            // Cập nhật thông tin user
            existingUser.setVerificationCode(verificationCode);
            existingUser.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(10));
            existingUser.setPasswordHash(encoder.encode(req.getPassword())); // Cập nhật password mới
            existingUser.setName(req.getName());
            existingUser.setUsername(req.getUsername());
            
            userRepo.save(existingUser);
            log.info("Updated existing user and resent verification code: {}", existingUser.getEmail());
            
            // Gửi email verification
            try {
                emailService.sendVerificationEmail(existingUser.getEmail(), existingUser.getUsername(), verificationCode);
                log.info("Verification email resent to: {}", existingUser.getEmail());
            } catch (Exception e) {
                log.error("Failed to resend verification email", e);
            }
            
            // Tạo confirm token
            String confirmToken = jwtProvider.generateVerificationToken(existingUser.getEmail(), verificationCode);
            
            return RegisterResponse.builder()
                    .resultCode("00035")
                    .resultMessageEn("Verification code resent. Please check your email.")
                    .resultMessageVn("Mã xác thực đã được gửi lại. Vui lòng kiểm tra email.")
                    .user(existingUser)
                    .confirmToken(confirmToken)
                    .build();
        }
        
        // User mới -> tạo mới
        log.info("Creating new user with email: {}", req.getEmail());
        
        // Tạo verification code 6 số
        String verificationCode = String.valueOf(100000 + new Random().nextInt(900000));
        
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .username(req.getUsername())
                .passwordHash(encoder.encode(req.getPassword()))
                .verificationCode(verificationCode)
                .verificationExpiresAt(LocalDateTime.now().plusMinutes(10))
                .isVerified(false)
                .isAdmin(false)
                .isActive(true)
                .build();
        
        userRepo.save(user);
        log.info("User saved successfully: {}", user.getEmail());
        
        // Gửi email verification
        try {
            emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), verificationCode);
            log.info("Verification email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send verification email", e);
            // Không throw exception - vẫn cho phép đăng ký thành công
            // User có thể request gửi lại code sau
        }
        
        // Tạo confirm token (không chứa code để bảo mật)
        String confirmToken = jwtProvider.generateVerificationToken(user.getEmail(), verificationCode);
        
        return RegisterResponse.builder()
                .resultCode("00035")
                .resultMessageEn("You registered successfully. Please check your email for verification code.")
                .resultMessageVn("Bạn đã đăng ký thành công. Vui lòng kiểm tra email để lấy mã xác thực.")
                .user(user)
                .confirmToken(confirmToken)
                .build();
    }

    public String sendVerificationCode(String email) {
        try {
            // Kiểm tra user tồn tại
            User user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
            
            // Tạo mã xác thực mới
            String code = String.valueOf(new Random().nextInt(900000) + 100000);
            
            // Cập nhật code và thời gian hết hạn
            user.setVerificationCode(code);
            user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(10));
            userRepo.save(user);
            
            // Gửi email
            emailService.sendVerificationEmail(email, user.getUsername(), code);
            log.info("Verification code sent to: {}", email);
            
            // Tạo confirm token
            String confirmToken = jwtProvider.generateVerificationToken(email, code);
            
            return confirmToken;
            
        } catch (Exception e) {
            log.error("Failed to send verification code to: {}", email, e);
            throw new RuntimeException("Failed to send verification code", e);
        }
    }

    public boolean verifyEmail(String code, String token) {
        // 1. Giải mã JWT token và lấy code/email
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProvider.getSecret())
                .parseClaimsJws(token)
                .getBody();

        String tokenCode = claims.get("code", String.class);
        String email = claims.getSubject();
        if (tokenCode == null || !tokenCode.equals(code)) {
            return false;
        }

        // 2. Cập nhật trạng thái người dùng
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        // Optional: kiểm tra hạn mã xác thực nếu đã lưu
        if (user.getVerificationExpiresAt() != null && user.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã xác thực đã hết hạn");
        }

        user.setIsVerified(true);
        user.setIsActive(true);
        user.setVerificationUsed(true);
        user.setVerificationToken(token);
        userRepo.save(user);

        return true;
    }

    public String getEmailFromToken(String token) {
        return jwtProvider.getEmailFromToken(token);
    }

    public String generateAccessTokenFromEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));
        return jwtProvider.generateAccessToken(user.getId(),email);
    }

    public String generateRefreshTokenFromEmail(String email) {
        return jwtProvider.generateRefreshToken(email);
    }
    public String verifyEmail(VerifyEmailRequest req) {
        try {
            User user = userRepo.findByEmail(req.getEmail())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));

            if (!user.getVerificationCode().equals(req.getCode()))
                throw new RuntimeException("Mã xác thực sai");

            user.setIsVerified(true);
            userRepo.save(user);
            return "Xác minh email thành công.";
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi thật ra console
            throw e;
        }
    }

    public String changePassword(ChangePasswordRequest req, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (!encoder.matches(req.getOldPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }

        user.setPasswordHash(encoder.encode(req.getNewPassword()));
        userRepo.save(user);
        return "Đổi mật khẩu thành công";
    }

    // ===================== SAVE/UPDATE TOKEN =====================
    public void saveOrUpdateToken(Long userId, String deviceId, String accessToken, String refreshToken, String fcmToken) {
        // Tìm token hiện tại của user trên device này
        Token existingToken = tokenRepo.findByUserIdAndDeviceIdAndIsActive(userId, deviceId, true)
                .orElse(null);
        
        if (existingToken != null) {
            // Update token cũ
            existingToken.setAccessToken(accessToken);
            existingToken.setRefreshToken(refreshToken);
            if (fcmToken != null) {
                existingToken.setFcmToken(fcmToken);
            }
            existingToken.setLastUsedAt(LocalDateTime.now());
            existingToken.setExpiresAt(LocalDateTime.now().plus(java.time.Duration.ofMillis(jwtProvider.getAccessExpiration())));
            tokenRepo.save(existingToken);
            log.info("Updated token for userId: {}, deviceId: {}", userId, deviceId);
        } else {
            // Tạo token mới
            Token newToken = Token.builder()
                    .userId(userId)
                    .deviceId(deviceId)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .fcmToken(fcmToken)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plus(java.time.Duration.ofMillis(jwtProvider.getAccessExpiration())))
                    .lastUsedAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            tokenRepo.save(newToken);
            log.info("Created new token for userId: {}, deviceId: {}", userId, deviceId);
        }
    }

    // ===================== LOGOUT =====================
    public void logout(Long userId, String deviceId) {
        // Invalidate token của device này
        tokenRepo.invalidateByUserIdAndDeviceId(userId, deviceId);
        log.info("Logged out userId: {}, deviceId: {}", userId, deviceId);
    }

    // ===================== LOGOUT ALL DEVICES =====================
    public void logoutAllDevices(Long userId) {
        // Invalidate tất cả token của user
        tokenRepo.invalidateAllByUserId(userId);
        log.info("Logged out all devices for userId: {}", userId);
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (!encoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new RuntimeException("Sai mật khẩu");

        String accessToken = jwtProvider.generateAccessToken(user.getId(),user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        user.setRefreshExpiresAt(LocalDateTime.now().plusDays(7));
        userRepo.save(user);

        return LoginResponse.builder()
                .resultMessage(new ResultMessage("You logged in successfully.", "Bạn đã đăng nhập thành công."))
                .resultCode("00047")
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public Map<String, String> refreshToken(String refreshToken) {
        // validate refreshToken, tạo accessToken & refreshToken mới
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", "newAccessTokenHere");
        tokens.put("refreshToken", "newRefreshTokenHere");
        return tokens;
    }

    public User validateLogin(LoginRequest request) {
        // 1️⃣ Lấy user từ database theo email
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        // 2️⃣ Kiểm tra mật khẩu
        if (!encoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        // 3️⃣ Kiểm tra email đã được verify chưa
        if (!user.getIsVerified()) {
            throw new RuntimeException("Email chưa được xác thực");
        }

        // 4️⃣ Kiểm tra user còn active không
        if (!user.getIsActive()) {
            throw new RuntimeException("Tài khoản bị khóa");
        }

        // 5️⃣ Cập nhật lastLogin (nếu muốn)
        user.setLastLogin(LocalDateTime.now());
        userRepo.save(user);

        // 6️⃣ Trả về user để tạo token
        return user;
    }

    // ===================== FORGOT PASSWORD =====================
    public Map<String, Object> forgotPassword(String email) {
        // Kiểm tra email có tồn tại không
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        
        // Kiểm tra tài khoản đã verify chưa
        if (!user.getIsVerified()) {
            throw new RuntimeException("Tài khoản chưa được xác thực. Vui lòng xác thực email trước.");
        }
        
        // Tạo mã reset 6 số
        String resetCode = String.valueOf(100000 + new Random().nextInt(900000));
        
        // Lưu reset code vào database (dùng lại field verificationCode)
        user.setVerificationCode(resetCode);
        user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(10));
        userRepo.save(user);
        
        log.info("Reset code generated for user: {}", email);
        
        // Gửi email chứa reset code
        try {
            emailService.sendPasswordResetEmail(email, user.getUsername(), resetCode);
            log.info("Password reset email sent to: {}", email);
        } catch (Exception e) {
            log.error("Failed to send password reset email", e);
            throw new RuntimeException("Không thể gửi email. Vui lòng thử lại sau.");
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("resultMessage", Map.of(
            "en", "Password reset code sent to your email",
            "vn", "Mã đặt lại mật khẩu đã được gửi đến email của bạn"
        ));
        response.put("resultCode", "00050");
        response.put("email", email);
        
        return response;
    }

    // ===================== RESET PASSWORD =====================
    public Map<String, Object> resetPassword(String email, String resetCode, String newPassword) {
        // Kiểm tra user có tồn tại không
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        
        // Kiểm tra reset code có đúng không
        if (!resetCode.equals(user.getVerificationCode())) {
            throw new RuntimeException("Mã đặt lại mật khẩu không đúng");
        }
        
        // Kiểm tra reset code có hết hạn không
        if (user.getVerificationExpiresAt() == null || 
            user.getVerificationExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã đặt lại mật khẩu đã hết hạn");
        }
        
        // Cập nhật password mới
        user.setPasswordHash(encoder.encode(newPassword));
        user.setVerificationCode(null); // Xóa reset code sau khi dùng
        user.setVerificationExpiresAt(null);
        userRepo.save(user);
        
        log.info("Password reset successfully for user: {}", email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("resultMessage", Map.of(
            "en", "Password reset successfully. You can now login with your new password",
            "vn", "Đặt lại mật khẩu thành công. Bạn có thể đăng nhập bằng mật khẩu mới"
        ));
        response.put("resultCode", "00051");
        
        return response;
    }

    // ===================== GETTER =====================
    public TokenRepository getTokenRepository() {
        return tokenRepo;
    }

}
