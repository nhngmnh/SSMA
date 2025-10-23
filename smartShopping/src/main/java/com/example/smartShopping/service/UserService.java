package com.example.smartShopping.service;

import com.example.smartShopping.configuration.JwtTokenProvider;
import com.example.smartShopping.dto.request.ChangePasswordRequest;
import com.example.smartShopping.dto.request.EditUserRequest;
import com.example.smartShopping.dto.request.VerifyEmailRequest;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider; // ✅ Dùng class bạn có sẵn

    // ------------------- ĐỔI MẬT KHẨU -------------------
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

    // ------------------- CHỈNH SỬA USER -------------------
    public String editUser(EditUserRequest req, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        user.setAvatarUrl(req.getAvatarUrl());
        userRepo.save(user);

        return "Cập nhật thông tin thành công";
    }

    // ------------------- XÁC THỰC EMAIL -------------------
    public Map<String, Object> verifyEmail(String code, String token) {
        Map<String, Object> response = new HashMap<>();

        // ✅ 1. Giải mã token để lấy email
        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // ✅ 2. Kiểm tra mã code
        if (!code.equals(user.getVerificationCode())) {
            response.put("resultMessage", Map.of(
                    "en", "Verification code is invalid.",
                    "vn", "Mã xác thực không hợp lệ."
            ));
            response.put("resultCode", "00059");
            return response;
        }

        // ✅ 3. Cập nhật trạng thái xác thực
        user.setIsVerified(true);
        userRepo.save(user);

        // ✅ 4. Sinh accessToken và refreshToken mới
        String accessToken = jwtTokenProvider.generateAccessToken(email);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);

        // ✅ 5. Trả về phản hồi
        response.put("resultMessage", Map.of(
                "en", "Your email address was verified successfully.",
                "vn", "Địa chỉ email của bạn đã được xác minh thành công."
        ));
        response.put("resultCode", "00058");
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        return response;
    }

    public void sendVerificationCode(VerifyEmailRequest req) {

    }
}
