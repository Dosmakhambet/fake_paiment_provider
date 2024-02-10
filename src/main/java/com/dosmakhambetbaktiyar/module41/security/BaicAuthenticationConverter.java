package com.dosmakhambetbaktiyar.module41.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class BaicAuthenticationConverter implements ServerAuthenticationConverter {

    private static final String basicPrefix = "Basic ";
    private static final Function<String, Mono<String>> getToken = authHeader -> Mono.justOrEmpty(authHeader.substring(basicPrefix.length()));

    private final BasicHandler basicHandler;
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return extractToken(exchange)
                .flatMap(getToken)
                .flatMap(basicHandler::verify)
                .flatMap(UserAuthenticationBearer::create);
    }

    private Mono<String> extractToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION));

    }
}
