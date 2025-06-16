package com.example.UserTaskManagerKeycloak.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
    public static String getUserIdFromToken(String token) {

        String secret = "thisIsASecretKeyThatIsAtLeast32Bytes!";
        byte[] secretBytes = secret.getBytes();

        token = token.replace("Bearer ", "");
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretBytes))
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject(); // Keycloak User ID
    }

}
