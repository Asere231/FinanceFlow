package com.example.authservice.dto;

import lombok.Data;

@Data
public class UserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String timezone;
}
