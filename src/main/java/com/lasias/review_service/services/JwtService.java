package com.lasias.review_service.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    public Long extractUserId(String token){
        return Long.parseLong(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());

    }

    public String extractJwt(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer != null && bearer.startsWith("Bearer")){
            return bearer.substring(7);
        }

        Cookie [] cookies = request.getCookies();
        if(cookies != null){
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("jwt"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;

    }

    public List<GrantedAuthority> extractAuthorities(String token){
        String role = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        if (role == null || role.isBlank()){
            return Collections.emptyList();
        }
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        return List.of(new SimpleGrantedAuthority(authority));
    }
}
