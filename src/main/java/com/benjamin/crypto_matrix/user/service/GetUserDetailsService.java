package com.benjamin.crypto_matrix.user.service;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;
import com.benjamin.crypto_matrix.utils.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserDetailsService {
    private final UserRepository userRepository;

    public Map<String, Object> getUserDetails() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(userEmail);
        return ResponseUtil.createSuccessResponse("User", user);
    }
}
