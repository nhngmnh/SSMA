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
    // Tìm token theo userId và deviceId
    Optional<Token> findByUserIdAndDeviceIdAndIsActive(Long userId, String deviceId, Boolean isActive);
    
    // Tìm tất cả token active của user
    List<Token> findByUserIdAndIsActive(Long userId, Boolean isActive);
    
    // Tìm token theo accessToken
    Optional<Token> findByAccessTokenAndIsActive(String accessToken, Boolean isActive);
    
    // Tìm token theo refreshToken
    Optional<Token> findByRefreshTokenAndIsActive(String refreshToken, Boolean isActive);
    
    // Invalidate token theo userId và deviceId
    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.isActive = false WHERE t.userId = ?1 AND t.deviceId = ?2")
    void invalidateByUserIdAndDeviceId(Long userId, String deviceId);
    
    // Invalidate tất cả token của user
    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.isActive = false WHERE t.userId = ?1")
    void invalidateAllByUserId(Long userId);
}