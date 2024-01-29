package com.dosmakhambetbaktiyar.module41.security;

import com.dosmakhambetbaktiyar.module41.model.User;
import com.dosmakhambetbaktiyar.module41.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final PBFDK2Encoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expirationInSec;
    @Value("${jwt.issuer}")
    private String issuer;

    private TokenDetails generateToken(User user){
        Map<String, Object> claims = new HashMap<>(){{
            put("role", user.getRole());
            put("username", user.getUsername());
        }};

        return generateToken(claims, user.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims, String subject){
        Long expirationInMillis = expirationInSec * 1000L;
        Date exprationDate = new Date(System.currentTimeMillis() + expirationInMillis);

        return generateToken(exprationDate, claims, subject);
    }
    private TokenDetails generateToken(Date exprationDate, Map<String, Object> claims, String subject) {
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(now)
                .setId(UUID.randomUUID().toString())
                .setExpiration(exprationDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();

        return TokenDetails.builder()
                .token(token)
                .issuedAt(now)
                .expiresAt(exprationDate)
                .build();
    }

    public Mono<TokenDetails> autenticate(String username, String password) {
        return userService.findByUsername(username)
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.just(generateToken(user).toBuilder()
                                .userId(user.getId())
                                .build());
                    } else {
                        return Mono.error(new RuntimeException("Password is incorrect"));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}
