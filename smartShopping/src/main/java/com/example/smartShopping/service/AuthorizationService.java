package com.example.smartShopping.service;

import com.example.smartShopping.configuration.JwtTokenProvider;
import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.GroupRepository;
import com.example.smartShopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Lấy User từ Authorization header
     */
    public User getUserFromAuth(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Lấy userId từ Authorization header
     */
    public Long getUserIdFromAuth(String authHeader) {
        return getUserFromAuth(authHeader).getUserId();
    }

    /**
     * Check xem user có phải admin không
     */
    public boolean isAdmin(String authHeader) {
        try {
            User user = getUserFromAuth(authHeader);
            return user.getIsAdmin() != null && user.getIsAdmin();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check xem user có phải owner của group không
     */
    public boolean isGroupOwner(Long userId, Long groupId) {
        if (groupId == null) return false;
        
        GroupEntity group = groupRepository.findById(groupId).orElse(null);
        if (group == null) return false;
        
        return group.getAdminId().equals(userId);
    }

    /**
     * Check xem user có phải owner của group không (từ authHeader)
     */
    public boolean isGroupOwner(String authHeader, Long groupId) {
        Long userId = getUserIdFromAuth(authHeader);
        return isGroupOwner(userId, groupId);
    }

    /**
     * Check xem user có phải thành viên của group không
     */
    public boolean isGroupMember(Long userId, Long groupId) {
        if (groupId == null) return false;
        
        GroupEntity group = groupRepository.findById(groupId).orElse(null);
        if (group == null) return false;
        
        // Owner cũng là member
        if (group.getAdminId().equals(userId)) return true;
        
        // Check trong danh sách members
        return group.getMembers() != null && group.getMembers().contains(userId);
    }

    /**
     * Check xem user có phải thành viên của group không (từ authHeader)
     */
    public boolean isGroupMember(String authHeader, Long groupId) {
        Long userId = getUserIdFromAuth(authHeader);
        return isGroupMember(userId, groupId);
    }

    /**
     * Validate quyền XEM dữ liệu cá nhân
     * - User phải là chủ sở hữu
     * - Hoặc là admin
     */
    public boolean canViewPersonalData(String authHeader, Long dataUserId) {
        if (isAdmin(authHeader)) return true;
        Long currentUserId = getUserIdFromAuth(authHeader);
        return currentUserId.equals(dataUserId);
    }

    /**
     * Validate quyền SỬA/XÓA dữ liệu cá nhân
     * - User phải là chủ sở hữu
     * - Hoặc là admin
     */
    public boolean canModifyPersonalData(String authHeader, Long dataUserId) {
        return canViewPersonalData(authHeader, dataUserId);
    }

    /**
     * Validate quyền XEM dữ liệu nhóm
     * - Phải là thành viên của group
     * - Hoặc là admin
     */
    public boolean canViewGroupData(String authHeader, Long groupId) {
        if (isAdmin(authHeader)) return true;
        
        return isGroupMember(authHeader, groupId);
    }

    /**
     * Validate quyền SỬA/XÓA dữ liệu nhóm
     * - Phải là owner của group
     * - Hoặc là admin
     */
    public boolean canModifyGroupData(String authHeader, Long groupId) {
        if (isAdmin(authHeader)) return true;
        
        return isGroupOwner(authHeader, groupId);
    }

    /**
     * Throw exception nếu không có quyền xem personal data
     */
    public void requireViewPersonalData(String authHeader, Long dataUserId) {
        if (!canViewPersonalData(authHeader, dataUserId)) {
            throw new RuntimeException("Access denied - You can only view your own data");
        }
    }

    /**
     * Throw exception nếu không có quyền sửa personal data
     */
    public void requireModifyPersonalData(String authHeader, Long dataUserId) {
        if (!canModifyPersonalData(authHeader, dataUserId)) {
            throw new RuntimeException("Access denied - You can only modify your own data");
        }
    }

    /**
     * Throw exception nếu không có quyền xem group data
     */
    public void requireViewGroupData(String authHeader, Long groupId) {
        if (!canViewGroupData(authHeader, groupId)) {
            throw new RuntimeException("Access denied - You must be a member of this group");
        }
    }

    /**
     * Throw exception nếu không có quyền sửa group data
     */
    public void requireModifyGroupData(String authHeader, Long groupId) {
        if (!canModifyGroupData(authHeader, groupId)) {
            throw new RuntimeException("Access denied - Only group owner or admin can modify this data");
        }
    }

    /**
     * Throw exception nếu không phải admin
     */
    public void requireAdmin(String authHeader) {
        if (!isAdmin(authHeader)) {
            throw new RuntimeException("Access denied - Admin permission required");
        }
    }

    /**
     * Lấy groupId của user (mỗi user chỉ thuộc 1 group)
     * - Nếu user là owner (adminId), tìm group mà user là owner
     * - Nếu user là member, tìm group mà user nằm trong members
     * - Return null nếu user không thuộc group nào
     */
    public Long getUserGroupId(Long userId) {
        // Check if user is owner of any group
        GroupEntity ownerGroup = groupRepository.findByAdminId(userId).orElse(null);
        if (ownerGroup != null) {
            return ownerGroup.getId();
        }
        
        // Check if user is member of any group
        java.util.List<GroupEntity> memberGroups = groupRepository.findGroupsByMember(userId);
        if (!memberGroups.isEmpty()) {
            return memberGroups.get(0).getId(); // User chỉ thuộc 1 group
        }
        
        return null; // User không thuộc group nào
    }

    /**
     * Lấy groupId của user từ authHeader
     */
    public Long getUserGroupId(String authHeader) {
        Long userId = getUserIdFromAuth(authHeader);
        return getUserGroupId(userId);
    }
}
