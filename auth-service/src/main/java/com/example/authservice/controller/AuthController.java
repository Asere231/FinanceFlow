package com.example.authservice.controller;

import com.example.authservice.dto.*;
import com.example.authservice.model.UserPrincipal;
import com.example.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("auth/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("auth/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("auth/profile")
    public ResponseEntity<ApiResponse<AuthResponse>> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return authService.getProfile(principal);
    }

    @PutMapping("auth/profile")
    public ResponseEntity<ApiResponse<AuthResponse>> updateProfile(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UpdateRequest updateRequest) {
        return authService.updateProfile(principal, updateRequest);
    }

    @DeleteMapping("auth/profile")
    public ResponseEntity<ApiResponse<AuthResponse>> deleteProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return authService.deleteProfile(principal);
    }

    @PostMapping("auth/change-password")
    public ResponseEntity<ApiResponse<AuthResponse>> changePassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String currentPassword, @RequestBody String newPassword) {
        return authService.changePassword(principal, currentPassword, newPassword);
    }
}
