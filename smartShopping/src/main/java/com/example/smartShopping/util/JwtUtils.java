package com.example.smartShopping.util;

import com.example.smartShopping.configuration.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Utility class để extract thông tin từ JWT token
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Extract userId từ Authorization header
     * @param authHeader Authorization header có format "Bearer {token}"
     * @return userId từ token
     * @throws RuntimeException nếu header invalid hoặc không có userId trong token
     */
    public Long extractUserIdFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Bỏ "Bearer "
        return jwtTokenProvider.getUserIdFromToken(token);
    }

    /**
     * Extract userId từ token (không có "Bearer " prefix)
     * @param token JWT token
     * @return userId từ token
     */
    public Long extractUserIdFromToken(String token) {
        return jwtTokenProvider.getUserIdFromToken(token);
    }

    /**
     * Extract email từ Authorization header
     * @param authHeader Authorization header có format "Bearer {token}"
     * @return email từ token
     */
    public String extractEmailFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        return jwtTokenProvider.getEmailFromToken(token);
    }
}
