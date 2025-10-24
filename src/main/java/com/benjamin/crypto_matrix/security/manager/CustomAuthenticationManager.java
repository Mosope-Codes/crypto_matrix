package com.benjamin.crypto_matrix.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager{
    private final UserRepository userRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByEmail(authentication.getName());
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        if(!comparePassword(authentication.getCredentials().toString(), user.getPassword())){
            throw new BadCredentialsException("Password mismatch");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
    }

    private Boolean comparePassword(String password, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.matches(password, hashedPassword);
    }
}
