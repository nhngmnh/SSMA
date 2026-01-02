package com.example.smartShopping.service;

import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.entity.User;
import com.example.smartShopping.repository.GroupRepository;
import com.example.smartShopping.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupEntity createGroup(Long adminId) {
        return groupRepository.findByAdminId(adminId)
                .orElseGet(() -> {
                    GroupEntity newGroup = GroupEntity.builder()
                            .adminId(adminId)
                            .groupName("Group of User " + adminId)
                            .build();

                    return groupRepository.save(newGroup);
                });
    }

    @Transactional
    public GroupEntity addMember(Long groupId, Long userId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getMembers().contains(userId)) {
            group.getMembers().add(userId);
        }

        return groupRepository.save(group);
    }

    @Transactional
    public GroupEntity removeMember(Long groupId, Long userId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.getMembers().remove(userId);

        return groupRepository.save(group);
    }

    public boolean isUserInGroup(Long groupId, Long userId) {
        return groupRepository.checkUserInGroup(groupId, userId).isPresent();
    }

    public List<GroupEntity> getGroupsOfMember(Long userId) {
        return groupRepository.findGroupsByMember(userId);
    }

    public GroupEntity getGroupByAdmin(Long userId) {
        return groupRepository.findByAdminId(userId)
                .orElseThrow(() -> new RuntimeException("Group for admin " + userId + " not found"));
    }

    // Lấy tất cả nhóm mà user tham gia (bao gồm cả admin và member)
    public List<GroupEntity> getAllGroupsOfUser(Long userId) {
        return groupRepository.findGroupsByMember(userId);
    }

    // Lấy thông tin chi tiết của một nhóm
    public GroupEntity getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
    }

    // Kiểm tra user có quyền truy cập group không (admin hoặc member)
    public boolean hasAccessToGroup(Long groupId, Long userId) {
        GroupEntity group = getGroupById(groupId);
        return group.getAdminId().equals(userId) || group.getMembers().contains(userId);
    }

    // Chuyển quyền sở hữu nhóm
    @Transactional
    public GroupEntity transferOwnership(Long groupId, Long currentAdminId, Long newAdminId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Kiểm tra quyền admin
        if (!group.getAdminId().equals(currentAdminId)) {
            throw new RuntimeException("Only group admin can transfer ownership");
        }

        // Kiểm tra user mới có tồn tại
        userRepository.findById(newAdminId)
                .orElseThrow(() -> new RuntimeException("New admin user not found"));

        // Kiểm tra user mới đã là member chưa
        if (!group.getMembers().contains(newAdminId)) {
            throw new RuntimeException("New admin must be a member of the group");
        }

        // Thêm admin cũ vào danh sách members nếu chưa có
        if (!group.getMembers().contains(currentAdminId)) {
            group.getMembers().add(currentAdminId);
        }

        // Xóa admin mới khỏi danh sách members
        group.getMembers().remove(newAdminId);

        // Chuyển quyền admin
        group.setAdminId(newAdminId);
        group.setUpdatedAt(LocalDateTime.now());

        return groupRepository.save(group);
    }

}
