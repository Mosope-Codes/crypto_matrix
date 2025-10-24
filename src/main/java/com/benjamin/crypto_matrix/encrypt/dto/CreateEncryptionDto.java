package com.benjamin.crypto_matrix.encrypt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEncryptionDto {
    @NotBlank(message = "email cannot be blank")
    private String recieverEmail;
    @NotBlank(message = "plaintext cannot be blank")
    private String plaintext;
}
