package com.github.felipe_pereiradev.dentalconnect.config.security;


import com.github.felipe_pereiradev.dentalconnect.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtServiceConfig {

    private final JwtEncoder encoder;

    private final static long JWT_EXPIRATION_SECONDS = 86400L;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        User user = (User) authentication.getPrincipal();

        List<String> scopes = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var claims = JwtClaimsSet.builder()
                .issuer("dentalconnect-security-jwt")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_EXPIRATION_SECONDS))
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", scopes)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}