package com.example.smartShopping.service;

import com.example.smartShopping.dto.request.*;
import com.example.smartShopping.dto.response.*;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.configuration.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtProvider;

    public RegisterResponse register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email đã tồn tại");

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .username(req.getUsername())
                .passwordHash(encoder.encode(req.getPassword()))
                .verificationCode(String.valueOf(100000 + new Random().nextInt(900000)))
                .verificationExpiresAt(LocalDateTime.now().plusMinutes(10))
                .isVerified(false)
                .isAdmin(false)
                .isActive(true)
                .build();

        userRepo.save(user);

        String confirmToken = jwtProvider.generateVerificationToken(user.getEmail(), user.getVerificationCode());

        return RegisterResponse.builder()
                .resultCode("00035")
                .resultMessageEn("You registered successfully.")
                .resultMessageVn("Bạn đã đăng ký thành công.")
                .user(user)
                .confirmToken(confirmToken)
                .build();
    }

    public String sendVerificationCode(String email) {
        // 1. Sinh mã xác thực (ví dụ 6 chữ số)
        String code = String.valueOf(new Random().nextInt(900000) + 100000);

        // 2. Tạo confirm token (JWT chứa email + code)
        String confirmToken = jwtProvider.generateVerificationToken(email, code); // tự viết method tạo JWT

        // 3. Gửi email (nếu muốn) - có thể dùng JavaMailSender

        return confirmToken;
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
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProvider.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
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


    public void logout() {

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

        // 3️⃣ Kiểm tra user còn active không
        if (!user.getIsActive()) {
            throw new RuntimeException("Tài khoản bị khóa");
        }

        // 4️⃣ Cập nhật lastLogin (nếu muốn)
        user.setLastLogin(LocalDateTime.now());
        userRepo.save(user);

        // 5️⃣ Trả về user để tạo token
        return user;
    }

}
