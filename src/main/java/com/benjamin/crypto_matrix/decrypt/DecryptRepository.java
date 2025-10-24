package com.benjamin.crypto_matrix.decrypt;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DecryptRepository extends JpaRepository<Decrypt, UUID>{
    
}
