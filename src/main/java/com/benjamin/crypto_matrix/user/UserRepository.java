package com.benjamin.crypto_matrix.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID>{
    User findByEmail(String email);
}
