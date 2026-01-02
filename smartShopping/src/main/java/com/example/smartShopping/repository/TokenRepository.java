package com.example.smartShopping.repository;

import com.example.smartShopping.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    // Tìm token theo userId và deviceId và isActive
    Optional<Token> findByUserIdAndDeviceIdAndIsActive(Long userId, String deviceId, Boolean isActive);
    
    // Tìm token theo userId và deviceId
    Optional<Token> findByUserIdAndDeviceId(Long userId, String deviceId);
    
    // Tìm tất cả token của user
    List<Token> findByUserId(Long userId);
    
    // Tìm token active theo access token
    Optional<Token> findByAccessTokenAndIsActive(String accessToken, Boolean isActive);
    
    // Tìm token active theo refresh token
    Optional<Token> findByRefreshTokenAndIsActive(String refreshToken, Boolean isActive);
    
    // Xóa token của user theo deviceId
    void deleteByUserIdAndDeviceId(Long userId, String deviceId);
    
    // Xóa tất cả token của user
    void deleteByUserId(Long userId);
    
    // Invalidate token theo userId và deviceId
    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.isActive = false WHERE t.userId = :userId AND t.deviceId = :deviceId")
    void invalidateByUserIdAndDeviceId(Long userId, String deviceId);
    
    // Invalidate tất cả token của user
    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.isActive = false WHERE t.userId = :userId")
    void invalidateAllByUserId(Long userId);
}