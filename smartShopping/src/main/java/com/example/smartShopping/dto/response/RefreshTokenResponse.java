package com.example.smartShopping.dto.response;

import java.util.Map;

public class RefreshTokenResponse {
    private Map<String, String> resultMessage;
    private String resultCode;
    private String accessToken;
    private String refreshToken;

    // constructor, getters, setters
    public RefreshTokenResponse(Map<String, String> resultMessage, String resultCode, String accessToken, String refreshToken) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public Map<String, String> getResultMessage() { return resultMessage; }
    public String getResultCode() { return resultCode; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }

    // getters & setters
}
