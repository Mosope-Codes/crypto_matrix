package com.benjamin.crypto_matrix.user;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.benjamin.crypto_matrix.user.dto.LoginUserDto;
import com.benjamin.crypto_matrix.user.dto.RegisterUserDto;
import com.benjamin.crypto_matrix.user.service.GetAllUsersService;
import com.benjamin.crypto_matrix.user.service.GetUserDetailsService;
import com.benjamin.crypto_matrix.user.service.LoginUserService;
import com.benjamin.crypto_matrix.user.service.RegisterUserService;
import com.benjamin.crypto_matrix.utils.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;
    private final GetUserDetailsService getUserDetailsService;
    private final GetAllUsersService getAllUsersService;
    

    @PostMapping("/auth/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) throws JsonProcessingException{
        Map<String,Object> user = registerUserService.registerUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody LoginUserDto loginUserDto){
        Map<String,Object> user = loginUserService.loginUser(loginUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Map<String, Object>> logoutUser(){
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("User logged out successfully"));
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(Pageable pageable){
        Map<String,Object> allUsers = getAllUsersService.getAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(allUsers);
    }

    @GetMapping("userDetails")
    public ResponseEntity<Map<String, Object>> getUserDetails(){
        Map<String,Object> userDetails = getUserDetailsService.getUserDetails();
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetails);
    }
}
