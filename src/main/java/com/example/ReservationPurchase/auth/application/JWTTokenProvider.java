package com.example.ReservationPurchase.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;



@Service
public class JWTTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expired-time.token.access}")
    private Long accessTokenExpiredTimeMs;

    @Value("${jwt.expired-time.token.refresh}")
    private Long refreshTokenExpiredTimeMs;

    public String generate(String email, String userName, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredTimeMs))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getKey(secretKey))
                .build().parseClaimsJws(token).getBody();
    }
    public String getUserName(String token){
        return extractClaims(token).get("userName", String.class);
    }
    public String getEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public long getExpiredTime(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        Date currentDate = new Date();
        return expiredDate.getTime() - currentDate.getTime();
    }

    public String generateAccess(String email, String userName) {
        return generate(email, userName, accessTokenExpiredTimeMs);
    }

    public String generateRefresh(String email, String userName) {
        return generate(email, userName, refreshTokenExpiredTimeMs);
    }


}
