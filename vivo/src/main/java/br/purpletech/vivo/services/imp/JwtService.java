package br.purpletech.vivo.services.imp;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    @Value("${encryption.key}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("role", authorities.iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7200000))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
}
