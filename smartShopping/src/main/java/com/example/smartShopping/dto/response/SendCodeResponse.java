package com.example.smartShopping.dto.response;

import java.util.Map;

public class SendCodeResponse {
    private Map<String, String> resultMessage;
    private String resultCode;
    private String confirmToken;

    public SendCodeResponse(Map<String, String> resultMessage, String resultCode, String confirmToken) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
        this.confirmToken = confirmToken;
    }

    // getters & setters
}
