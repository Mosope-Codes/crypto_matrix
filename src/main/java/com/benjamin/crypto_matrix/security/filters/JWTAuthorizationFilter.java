package com.benjamin.crypto_matrix.security.filters;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.benjamin.crypto_matrix.security.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException 
        {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.replace("Bearer ", "");
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                        .build()
                        .verify(token)
                        .getSubject();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
