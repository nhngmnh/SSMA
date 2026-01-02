package com.example.smartShopping.repository;

import com.example.smartShopping.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByAdminId(Long adminId);

    @Query(value = "SELECT * FROM groups WHERE members @> CAST(:userId AS BIGINT[])",
            nativeQuery = true)
    List<GroupEntity> findGroupsByMember(Long userId);

    @Query(value = "SELECT * FROM groups WHERE group_id = :groupId AND members @> CAST(:userId AS BIGINT[])",
            nativeQuery = true)
    Optional<GroupEntity> checkUserInGroup(Long groupId, Long userId);

    // Lấy tất cả groups mà user là member hoặc owner
    @Query(value = "SELECT * FROM groups WHERE created_by = :adminId OR members @> CAST(:userId AS BIGINT[])",
            nativeQuery = true)
    List<GroupEntity> findAllByMembersContainingOrAdminId(Long userId, Long adminId);
}
