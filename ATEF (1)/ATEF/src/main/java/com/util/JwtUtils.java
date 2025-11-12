
    // File: src/main/java/com/util/JwtUtils.java
package com.util;

    import io.jsonwebtoken.*;
    import org.springframework.stereotype.Component;
    import java.util.Date;

    @Component
    public class JwtUtils {

        private static final String SECRET_KEY = "e5oUmRX6bf26p/CAM0Z3e3tQeSTZh3CyNPsK1P/ueo8=";
        private static final long EXPIRATION_TIME = 86400000; // 1 day in ms

        public String generateToken(String username) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                return false;
            }
        }

        public String extractUsername(String token) {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
    }

