package com.example.smartShopping.controller;

import com.example.smartShopping.dto.request.TransferOwnershipRequest;
import com.example.smartShopping.dto.response.GroupResponse;
import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.UserRepository;
import com.example.smartShopping.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserRepository userRepository;

    /**
     * Lấy danh sách tất cả nhóm của user
     * GET /api/groups
     */
    @GetMapping
    public Object getAllGroupsOfUser() {
        try {
            Long userId = getCurrentUserId();

            List<GroupEntity> groups = groupService.getAllGroupsOfUser(userId);

            List<GroupResponse.GroupData> groupDataList = new ArrayList<>();
            for (GroupEntity group : groups) {
                GroupResponse.GroupData groupData = buildGroupData(group);
                groupDataList.add(groupData);
            }

            Map<String, Object> response = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "Successfully retrieved user groups");
            resultMessage.put("vn", "Lấy danh sách nhóm thành công");
            response.put("resultMessage", resultMessage);
            response.put("resultCode", "1000");
            response.put("groups", groupDataList);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Lấy thông tin chi tiết của một nhóm
     * GET /api/groups/{id}
     */
    @GetMapping("/{id}")
    public Object getGroupById(@PathVariable Long id) {
        try {
            Long userId = getCurrentUserId();

            // Kiểm tra quyền truy cập
            if (!groupService.hasAccessToGroup(id, userId)) {
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                Map<String, String> resultMessage = new LinkedHashMap<>();
                resultMessage.put("en", "You don't have permission to access this group");
                resultMessage.put("vn", "Bạn không có quyền truy cập nhóm này");
                errorResponse.put("resultMessage", resultMessage);
                errorResponse.put("resultCode", "1403");
                return ResponseEntity.status(403).body(errorResponse);
            }

            GroupEntity group = groupService.getGroupById(id);
            GroupResponse.GroupData groupData = buildGroupData(group);

            Map<String, Object> response = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "Successfully retrieved group details");
            resultMessage.put("vn", "Lấy thông tin nhóm thành công");
            response.put("resultMessage", resultMessage);
            response.put("resultCode", "1000");
            response.put("groupData", groupData);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "Group not found: " + e.getMessage());
            resultMessage.put("vn", "Không tìm thấy nhóm: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1404");
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Chuyển quyền sở hữu nhóm
     * PUT /api/groups/{id}/transfer-ownership
     */
    @PutMapping("/{id}/transfer-ownership")
    public Object transferOwnership(
            @PathVariable Long id,
            @RequestBody TransferOwnershipRequest request) {
        try {
            Long currentAdminId = getCurrentUserId();

            GroupEntity updatedGroup = groupService.transferOwnership(
                    id,
                    currentAdminId,
                    request.getNewAdminId()
            );

            GroupResponse.GroupData groupData = buildGroupData(updatedGroup);

            Map<String, Object> response = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "Successfully transferred group ownership");
            resultMessage.put("vn", "Chuyển quyền chủ nhóm thành công");
            response.put("resultMessage", resultMessage);
            response.put("resultCode", "1000");
            response.put("groupData", groupData);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "Transfer ownership failed: " + e.getMessage());
            resultMessage.put("vn", "Chuyển quyền thất bại: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1400");
            return ResponseEntity.status(400).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            Map<String, String> resultMessage = new LinkedHashMap<>();
            resultMessage.put("en", "System error: " + e.getMessage());
            resultMessage.put("vn", "Lỗi hệ thống: " + e.getMessage());
            errorResponse.put("resultMessage", resultMessage);
            errorResponse.put("resultCode", "1999");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // ==================== Helper Methods ====================

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getName());
    }

    private GroupResponse.GroupData buildGroupData(GroupEntity group) {
        List<GroupResponse.MemberData> memberDataList = new ArrayList<>();

        // Thêm thông tin admin
        Optional<User> adminUser = userRepository.findById(group.getAdminId());
        adminUser.ifPresent(user -> memberDataList.add(
                GroupResponse.MemberData.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .fullName(user.getName())
                        .email(user.getEmail())
                        .build()
        ));

        // Thêm thông tin các members
        for (Long memberId : group.getMembers()) {
            Optional<User> memberUser = userRepository.findById(memberId);
            memberUser.ifPresent(user -> memberDataList.add(
                    GroupResponse.MemberData.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .fullName(user.getName())
                            .email(user.getEmail())
                            .build()
            ));
        }

        return GroupResponse.GroupData.builder()
                .groupId(group.getId())
                .groupName(group.getGroupName())
                .adminId(group.getAdminId())
                .members(memberDataList)
                .build();
    }
}
