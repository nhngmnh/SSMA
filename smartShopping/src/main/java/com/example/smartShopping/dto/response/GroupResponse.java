package com.example.smartShopping.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupResponse {

    private ResultMessage resultMessage;
    private String resultCode;
    private GroupData groupData;

    @Data
    @Builder
    public static class ResultMessage {
        private String en;
        private String vn;
    }

    @Data
    @Builder
    public static class GroupData {
        private Long groupId;
        private String groupName;
        private Long adminId;
        private List<MemberData> members;
    }

    @Data
    @Builder
    public static class MemberData {
        private Long userId;
        private String username;
        private String fullName; // nếu có
        private String email;    // nếu có
    }
}
