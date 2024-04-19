package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "Nayak@111";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException e) {
            // Invalid token
            return null;
        }
    }
}
