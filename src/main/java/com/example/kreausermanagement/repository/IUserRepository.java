package com.example.kreausermanagement.repository;

import com.example.kreausermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    User findByEmail(String email);
}
