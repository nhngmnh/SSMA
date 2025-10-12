package com.example.smartShopping.configuration;

import com.example.smartShopping.dto.response.ApiResponse;
import com.example.smartShopping.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(ErrorCode.UNAUTHENTICATION.getStatus().value());
        response.setContentType("application/json");
        ApiResponse<Object> body = new ApiResponse<>(
                false,
                ErrorCode.UNAUTHENTICATION.getMessage(),
                ErrorCode.UNAUTHENTICATION.getCode(),
                null
        );
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}