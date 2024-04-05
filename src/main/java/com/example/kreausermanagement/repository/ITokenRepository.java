package com.example.kreausermanagement.repository;

import com.example.kreausermanagement.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
        select t from Token t inner join User u on t.user.id = u.id
        where t.user.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
