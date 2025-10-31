package com.example.smartShopping.service;

import com.example.smartShopping.entity.GroupEntity;
import com.example.smartShopping.repository.GroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

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

}
