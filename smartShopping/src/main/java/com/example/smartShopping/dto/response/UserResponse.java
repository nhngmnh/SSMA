package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String email;
    String password; // có thể null hoặc ẩn
    String username;
    String name;
    String type;
    String language;
    String gender;
    String countryCode;
    String timezone;
    String photoUrl;
    boolean isActivated;
    boolean isVerified;
    String deviceId;
    int belongsToGroupAdminId;
    String createdAt;
    String updatedAt;


}

