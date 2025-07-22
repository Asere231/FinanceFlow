package com.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private UserResponse user;
    private String token;

    public AuthResponse(UserResponse user) {
        this.user = user;
    }
}
