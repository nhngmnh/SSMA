package com.example.smartShopping.dto.response;

import java.util.Map;

public class VerifyEmailResponse {
    private Map<String, String> resultMessage;
    private String resultCode;
    private String accessToken;
    private String refreshToken;

    public VerifyEmailResponse(Map<String, String> resultMessage, String resultCode, String accessToken, String refreshToken) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // getters & setters
}
