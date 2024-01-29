package com.dosmakhambetbaktiyar.module41.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

public class JwtHandler {

    private final String secret;

    public JwtHandler(String secret) {
        this.secret = secret;
    }

    public Mono<VerifyResult> verify(String accessToken) {
        return Mono.just(verifyResult(accessToken))
                .onErrorResume(e -> Mono.error(new RuntimeException("Invalid token")));
    }

    private VerifyResult verifyResult(String token) {
        Claims claims = getCliams(token);
        final Date expiration = claims.getExpiration();

        if(expiration.before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        return new VerifyResult(claims, token);
    }
    private Claims getCliams(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }
    public static class VerifyResult {
        public Claims claims;
        public String token;

        public VerifyResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}
