package com.example.smartShopping.dto.response;

import lombok.Data;

@Data
public class LogResponse {
    private Long id;
    private Long userId;
    private String resultCode;
    private String level;
    private String errorMessage;
    private String ip;
    private String createdAt;
    private String updatedAt;
}
