package com.benjamin.crypto_matrix.user.service;

import java.util.Date;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.benjamin.crypto_matrix.security.SecurityConstants;
import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;
import com.benjamin.crypto_matrix.user.dto.LoginUserDto;
import com.benjamin.crypto_matrix.utils.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserService {
    private final UserRepository userRepository;

    public Map<String, Object> loginUser(LoginUserDto loginUserDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.findByEmail(loginUserDto.getEmail());

        if(user == null){
            throw new RuntimeException("User not found");
        }
        if(!encoder.matches(loginUserDto.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = generateJWT(loginUserDto.getEmail());
        return ResponseUtil.createSuccessResponse("User logged in successfully", token);
    }

    public String generateJWT(String email){
        String token = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));

        return token;
    }
}
