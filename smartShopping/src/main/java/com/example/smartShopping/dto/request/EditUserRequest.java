package com.example.smartShopping.dto.request;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserRequest {
    private String name;
    private String phone;
    private String address;
    private String avatarUrl;
}
