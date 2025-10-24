package com.benjamin.crypto_matrix.encrypt;


import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.benjamin.crypto_matrix.user.KeyMatrix;
import com.benjamin.crypto_matrix.user.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "encryptions")
public class Encrypt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "plaintext", nullable = false, columnDefinition = "TEXT")
    private String plaintext;

    @Column(name = "ciphertext", nullable = false, columnDefinition = "TEXT")
    private String ciphertext;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "key_matrix_id", referencedColumnName = "id")
    private KeyMatrix keyMatrix;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
}
