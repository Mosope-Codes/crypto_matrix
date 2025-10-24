package com.benjamin.crypto_matrix.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
