package com.dosmakhambetbaktiyar.module41.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class BearerTokenAuthenticationConverter implements ServerAuthenticationConverter {
    private final JwtHandler jwtHandler;
    private static final String bearerPrefix = "Bearer ";
    private static final Function<String, Mono<String>> getToken = authHeader -> Mono.justOrEmpty(authHeader.substring(bearerPrefix.length()));
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return extractToken(exchange)
                .flatMap(getToken)
                .flatMap(jwtHandler::verify)
                .flatMap(UserAuthenticationBearer::create);
    }

    private Mono<String> extractToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION));

    }
}
