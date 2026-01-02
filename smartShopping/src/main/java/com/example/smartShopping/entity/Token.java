package com.example.smartShopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(length = 100)
    private String deviceId;
    
    @Column(length = 1000)
    private String accessToken;
    
    @Column(length = 1000)
    private String refreshToken;
    
    @Column(length = 500)
    private String fcmToken;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt;
    private LocalDateTime lastUsedAt;
    
    @Column(nullable = false)
    private Boolean isActive = true;
}