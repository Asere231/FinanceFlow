package com.example.authservice.model;

import com.example.authservice.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @JsonIgnore
    private String password;

    private String timezone;

    /**
     * Convert this Entity into a DTO Response.
     */
    public UserResponse toResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(this.firstName);
        userResponse.setLastName(this.lastName);
        userResponse.setEmail(this.email);
        userResponse.setTimezone(this.timezone);
        return userResponse;
    }
}
