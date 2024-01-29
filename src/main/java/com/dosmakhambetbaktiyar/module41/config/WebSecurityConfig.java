package com.dosmakhambetbaktiyar.module41.config;

import com.dosmakhambetbaktiyar.module41.security.AuthenticationManager;
import com.dosmakhambetbaktiyar.module41.security.BearerTokenAuthenticationConverter;
import com.dosmakhambetbaktiyar.module41.security.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    private final String [] publicRouters = {"api/v1/auth/login", "api/v1/auth/register"};

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) {

        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(publicRouters).permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->{
                    log.error("Unauthorized error: {}", e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                })
                .accessDeniedHandler((swe, e) -> {
                    log.error("Forbidden error: {}", e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                })
                .and()
                .addFilterAt(bearerAuthenticationFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private AuthenticationWebFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationFilter.setServerAuthenticationConverter(new BearerTokenAuthenticationConverter(new JwtHandler(secret)));
        authenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

        return authenticationFilter;
    }
}
