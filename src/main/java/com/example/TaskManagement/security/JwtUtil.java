package com.example.TaskManagement.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "YourSuperSecretKeyForJWTWhichShouldBeLongEnough";

    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    public String generateToken(String email){

        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token){

        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean ValidateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSigningKey())
                    .build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


}
