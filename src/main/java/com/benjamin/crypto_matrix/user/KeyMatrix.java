package com.benjamin.crypto_matrix.user;


import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "key_matrices")
public class KeyMatrix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "public_key_matrix", nullable = false)
    private String publicKeyMatrix;

    @Column(name = "private_key_matrix", nullable = false)
    private String privateKeyMatrix;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)  
    private Timestamp updatedAt;

}
