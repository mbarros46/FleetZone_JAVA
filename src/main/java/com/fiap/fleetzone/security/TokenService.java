package com.fiap.fleetzone.security;

import com.fiap.fleetzone.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    private final SecretKey key;
    private final long expirationMillis;

    public TokenService(@Value("${jwt.secret:}") String secret,
                        @Value("${jwt.secret-base64:}") String secretBase64,
                        @Value("${jwt.expiration-ms:0}") long expirationMsFallback,
                        @Value("${jwt.expiration:0}") long expirationFallback) {
        // support either plain secret (utf8) or base64 secret (preferred)
        if (secretBase64 != null && !secretBase64.isBlank()) {
            byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secretBase64);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } else if (secret != null && !secret.isBlank()) {
            this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } else {
            throw new IllegalStateException("JWT secret not configured (jwt.secret or jwt.secret-base64)");
        }

        // prefer expiration-ms; fall back to legacy jwt.expiration
        if (expirationMsFallback > 0) this.expirationMillis = expirationMsFallback;
        else if (expirationFallback > 0) this.expirationMillis = expirationFallback;
        else this.expirationMillis = 86400000L; // default 24h
    }

    public String generate(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("name", user.getNome())
                .claim("email", user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(key)
                .compact();
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser().verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (io.jsonwebtoken.JwtException ex) {
            throw new IllegalArgumentException("Invalid token", ex);
        }
    }

    public String getEmail(String token) {
        try {
            return Jwts.parser().verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("email", String.class);
        } catch (io.jsonwebtoken.JwtException ex) {
            return null;
        }
    }

    public long getExpirationMillis() { return expirationMillis; }
}