package com.benjamin.crypto_matrix.encrypt;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EncryptRepository extends JpaRepository<Encrypt, UUID>{
    
}
