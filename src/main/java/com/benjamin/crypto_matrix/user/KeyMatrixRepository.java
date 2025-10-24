package com.benjamin.crypto_matrix.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyMatrixRepository extends JpaRepository<KeyMatrix, UUID>{
    
}
