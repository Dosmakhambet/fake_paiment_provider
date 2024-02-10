package com.dosmakhambetbaktiyar.module41.security;


import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Base64;

@NoArgsConstructor
public class BasicHandler {

    public Mono<String[]> verify(String token) {
        return Mono.just(verifyResult(token))
                .onErrorResume(e -> Mono.error(new RuntimeException("Invalid token")));
    }

    private String[] verifyResult(String token) {
        try {
            String credentials = new String(Base64.getDecoder().decode(token));
            String[] parts = credentials.split(":");

            if (parts.length != 2) {
                throw new RuntimeException("Invalid token");
            }

            return parts;
        }catch (Exception e){
            throw new RuntimeException("Invalid token");
        }
    }
}
