package com.benjamin.crypto_matrix.user.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;
import com.benjamin.crypto_matrix.utils.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllUsersService {
    private final UserRepository userRepository;

    public Map<String, Object> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return ResponseUtil.createSuccessResponse("Users", users);
    }
    
}
