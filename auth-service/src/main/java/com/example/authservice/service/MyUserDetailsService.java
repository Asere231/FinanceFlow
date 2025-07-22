package com.example.authservice.service;

import com.example.authservice.model.User;
import com.example.authservice.model.UserPrincipal;
import com.example.authservice.repo.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = authRepo.findUserByEmail(username);

        return new UserPrincipal(user);
    }
}
