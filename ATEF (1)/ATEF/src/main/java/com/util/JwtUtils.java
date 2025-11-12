
    // File: src/main/java/com/util/JwtUtils.java
package com.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

    @Component
    public class JwtUtils {

        // Inject a secret key from application.properties
        @Value("${jwt.secret:a-very-long-secret-key-that-should-be-at-least-256-bits-and-changed-in-production}")
        private String secret;

        // Token expiration time in milliseconds (e.g., 24 hours)
        private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

        // --- Private Key Generation ---

        private Key getSigningKey() {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        // --- Token Generation ---

        public String generateToken(String username) {
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, username);
        }

        private String createToken(Map<String, Object> claims, String subject) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        // --- Token Validation ---

        public Boolean validateToken(String token) {
            try {
                // Check if the token is not expired and the signature is valid
                return !isTokenExpired(token);
            } catch (Exception e) {
                // Log exception or return false if token is invalid (e.g., malformed, bad signature)
                return false;
            }
        }

        // --- Claims Extraction ---

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
}

}
