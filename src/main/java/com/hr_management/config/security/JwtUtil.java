package com.hr_management.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetailsCustom userDetailsCustom) {
        List<String> roles = userDetailsCustom.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .claim("id", userDetailsCustom.getId())
                .subject(userDetailsCustom.getUsername())
                .claim("nhanSuId", userDetailsCustom.getNhanSuId().toString())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetailsCustom userDetailsCustom) {
        final String username = extractUsername(token);
        return username.equals(userDetailsCustom.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpiringSoon(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        long timeLeft = expiration.getTime() - System.currentTimeMillis();
        return timeLeft < 5 * 60 * 1000;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}