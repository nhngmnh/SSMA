package com.example.smartShopping.dto.response;

import com.example.smartShopping.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {
    String resultCode;
    String resultMessageEn;
    String resultMessageVn;
    User user;
    String confirmToken;
}
