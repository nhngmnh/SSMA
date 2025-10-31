package com.example.smartShopping.dto.request;

import lombok.Data;

@Data
public class VerifyEmailRequest {
    private String code;   // Mã xác thực do người dùng nhập
    private String token;  // JWT token được gửi kèm link xác minh

    public String getEmail() {
        return "";
    }
}
