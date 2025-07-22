package com.example.authservice.service;

import com.example.authservice.dto.*;
import com.example.authservice.model.User;
import com.example.authservice.model.UserPrincipal;
import com.example.authservice.repo.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepo authRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public ResponseEntity<ApiResponse<AuthResponse>> register(RegisterRequest registerRequest) {

        try {
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            User user = authRepo.save(registerRequest.toEntity());
            UserResponse userResponse = user.toResponse();
            String token = null;
            AuthResponse authResponse = new AuthResponse(userResponse, token);
            ApiResponse<AuthResponse> apiResponse =
                    new ApiResponse<>(true, authResponse, "User saved!");
            System.out.println("User saved!");

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AuthResponse>> login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        String token = jwtService.generateToken(email, "USER");

        try {
            UserResponse userResponse = authRepo.findUserByEmail(email).toResponse();
            AuthResponse authResponse = new AuthResponse(userResponse, token);
            ApiResponse<AuthResponse> apiResponse =
                    new ApiResponse<>(true, authResponse, "Success login");

            System.out.println("Success login");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AuthResponse>> getProfile(UserPrincipal principal) {

        try {
            UserResponse userResponse = authRepo.findUserByEmail(principal.getUsername()).toResponse();
            AuthResponse authResponse = new AuthResponse(userResponse);
            ApiResponse<AuthResponse> apiResponse =
                    new ApiResponse<>(true, authResponse, "Got profile");

            System.out.println("Got profile");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AuthResponse>> updateProfile(UserPrincipal principal, UpdateRequest updateRequest) {

        try {
            User user = authRepo.findUserByEmail(principal.getUsername());

            if (!user.getFirstName().equals(updateRequest.getFirstName()))
                user.setFirstName(updateRequest.getFirstName());
            if (!user.getLastName().equals(updateRequest.getLastName()))
                user.setLastName(updateRequest.getLastName());
            if (!user.getTimezone().equals(updateRequest.getTimezone()))
                user.setTimezone(updateRequest.getTimezone());

            UserResponse userResponse = authRepo.save(user).toResponse();
            AuthResponse authResponse = new AuthResponse(userResponse);
            ApiResponse<AuthResponse> apiResponse =
                    new ApiResponse<>(true, authResponse, "User updated!!");

            System.out.println("User updated!!");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AuthResponse>> deleteProfile(UserPrincipal principal) {

        try {
            String email = principal.getUsername();
            authRepo.deleteUserByEmail(email);
            AuthResponse authResponse = new AuthResponse();
            ApiResponse<AuthResponse> apiResponse =
                    new ApiResponse<>(true, authResponse, "Profile Deleted");

            System.out.println("Profile Deleted");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<AuthResponse>> changePassword(UserPrincipal principal, String currentPassword, String newPassword) {

        String email = principal.getUsername();
        String validCurrentPassword = principal.getPassword();

        if (validCurrentPassword.equals(currentPassword)) {
            AuthResponse authResponse = new AuthResponse();
            ApiResponse<AuthResponse> apiResponse =
                    new ApiResponse<>(false, authResponse, "The password entered is not the same as your current password");

            System.out.println("The password entered is not the same as your current password");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }

        try {
            User user = authRepo.findUserByEmail(email);
            user.setPassword(passwordEncoder.encode(newPassword));

            UserResponse userResponse = authRepo.save(user).toResponse();
            AuthResponse authResponse = new AuthResponse(userResponse);
            ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(true, authResponse, "Password changed");

            System.out.println("Password changed");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
