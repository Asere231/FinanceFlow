package com.example.authservice.dto;

import com.example.authservice.model.User;
import lombok.Data;

@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String timezone;

    /**
     * Convert this DTO into a JPA User entity.
     */
    public User toEntity() {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setTimezone(this.timezone);
        return user;
    }
}
