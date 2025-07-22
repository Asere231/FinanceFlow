package com.example.authservice.repo;

import com.example.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
    void deleteUserByEmail(String email);
}
