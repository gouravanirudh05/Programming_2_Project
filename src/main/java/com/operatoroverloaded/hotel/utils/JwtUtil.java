package com.operatoroverloaded.hotel.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "yourSecretKey"; // Use a secure secret key in production

    // Generate a JWT token
    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // User ID is stored as the subject
                .setIssuedAt(new Date()) // Token creation date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1-hour expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Use HS256 for signing
                .compact();
    }

    // Validate the token and retrieve claims
    public static Claims validateToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
