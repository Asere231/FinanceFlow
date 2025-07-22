package com.example.accountservice.repo;

import com.example.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {


    List<Account> findAccountsByUserEmail(String userEmail);
}
