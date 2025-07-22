package com.example.authservice.dto;

import com.example.authservice.model.User;
import lombok.Data;

@Data
public class UpdateRequest {

    private String firstName;
    private String lastName;
    private String timezone;
}


