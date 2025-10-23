package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private ResultMessage resultMessage;
    private String resultCode;
    private User user;
    private String accessToken;
    private String refreshToken;
}