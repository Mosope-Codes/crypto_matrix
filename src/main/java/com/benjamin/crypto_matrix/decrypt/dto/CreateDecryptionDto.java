package com.benjamin.crypto_matrix.decrypt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDecryptionDto {
    @NotBlank(message = "Ciphertext cannot be blank")
    private String ciphertext;
}
