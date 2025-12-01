package com.example.smartShopping.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, length = 50)
    private String username;

    private String gender;
    private LocalDate dateOfBirth;
    private String language = "vi";
    private String timezone = "Asia/Ho_Chi_Minh";
    private String deviceId;
    private String profileImageUrl;
    private Boolean isVerified = false;
    private Boolean isActive = false;
    private Boolean isAdmin = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime lastLogin;
    private String verificationCode;
    private String verificationToken;
    private LocalDateTime verificationExpiresAt;
    private Boolean verificationUsed = false;
    private String refreshToken;
    private LocalDateTime refreshExpiresAt;
    private Boolean refreshRevoked = false;

    // 🆕 Các trường mở rộng
    private String phone;
    private String address;
    private String avatarUrl;

    // ✅ 🆕 Thêm đúng trường notificationToken
    @Column(name = "notification_token")
    private String notificationToken;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setNotificationToken(String token) {
        this.notificationToken = token;
    }



}
