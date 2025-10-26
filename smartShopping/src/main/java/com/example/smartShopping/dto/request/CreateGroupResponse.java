package com.example.smartShopping.dto.request;

import java.util.Map;

public class CreateGroupResponse {

    private Map<String, String> resultMessage;
    private String resultCode;
    private Long adminId;

    public CreateGroupResponse(Long adminId) {
        this.adminId = adminId;
        this.resultCode = "00095";
        this.resultMessage = Map.of(
                "en", "Your group has been created successfully",
                "vn", "Tạo nhóm thành công"
        );
    }

    public Map<String, String> getResultMessage() {
        return resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public Long getAdminId() {
        return adminId;
    }
}
