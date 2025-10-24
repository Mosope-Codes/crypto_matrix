package com.benjamin.crypto_matrix.security.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch(EntityNotFoundException ex){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("User not found");
            response.getWriter().flush();
        } catch(JWTVerificationException ex){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT Verification Failed");
            response.getWriter().flush();
        } catch(RuntimeException ex){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Bad Request");
            response.getWriter().flush(); 
        }
    }
}
