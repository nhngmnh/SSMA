package com.example.smartShopping.repository;

import com.example.smartShopping.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
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
}