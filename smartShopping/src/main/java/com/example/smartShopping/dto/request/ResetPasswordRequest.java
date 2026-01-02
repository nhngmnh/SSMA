package com.example.smartShopping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("resetCode")
    private String resetCode;
    
    @JsonProperty("newPassword")
    private String newPassword;
}
