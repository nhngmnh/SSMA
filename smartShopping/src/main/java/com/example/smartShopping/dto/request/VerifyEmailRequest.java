package com.example.smartShopping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyEmailRequest {
    @JsonProperty("verificationCode")
    private String code;   // Mã xác thực 6 chữ số
    
    @JsonProperty("confirmToken")
    private String token;  // JWT token nhận được từ register response

    public String getEmail() {
        return "";
    }
}
