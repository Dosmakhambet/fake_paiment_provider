package com.dosmakhambetbaktiyar.module41.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

public class UserAuthenticationBearer {
    public static Mono<Authentication> create(String[] parts) {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("MERCHANT"));
        CustomPrincipal principal = new CustomPrincipal(parts[0], parts[1]);
        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(principal, null, authorities));
    }
}
