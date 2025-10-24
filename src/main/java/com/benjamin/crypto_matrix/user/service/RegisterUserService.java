package com.benjamin.crypto_matrix.user.service;

import java.util.Map;

import org.ejml.simple.SimpleMatrix;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.user.KeyMatrix;
import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;
import com.benjamin.crypto_matrix.user.dto.RegisterUserDto;
import com.benjamin.crypto_matrix.utils.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final GenerateKeyMatrixService generateKeyMatrixService;
    private final SendEmailService sendEmailService;

    public Map<String, Object> registerUser(RegisterUserDto registerUserDto) throws JsonProcessingException{
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(hashPassword(registerUserDto.getPassword()));
        user.setKeyMatrix(generateKeyMatrixService.generateKeyMatrixForUser());
        User registeredUser = userRepository.save(user);
        sendKeyMatrixToUserEmail(registeredUser);
        return ResponseUtil.createSuccessResponse("User registered successfully!");

    }

    public void sendKeyMatrixToUserEmail(User user){
        String userEmail = user.getEmail();
        KeyMatrix keyMatrix = user.getKeyMatrix();
        String emailBody = "Your Public Key Matrix:\n" + keyMatrix.getPublicKeyMatrix() + "\n\nYour Private Key Matrix:\n" + keyMatrix.getPrivateKeyMatrix() +
                            "\n\nKeep your private key secure and do not share it with anyone.";

        sendEmailService.sendEmail(userEmail, "Your Key Matrices", emailBody);

    }

    public String hashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
