package com.example.to_do_server.user.domain.repository;

import com.example.to_do_server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    void deleteByUserId(String userId);

    boolean existsByUserId(String userId);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}