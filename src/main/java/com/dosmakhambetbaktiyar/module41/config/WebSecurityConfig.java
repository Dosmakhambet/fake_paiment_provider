package com.dosmakhambetbaktiyar.module41.config;

import com.dosmakhambetbaktiyar.module41.security.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) {

        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .securityContextRepository(securityContextRepository())
                .addFilterAt(authenticationWebFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter authenticationWebFilter(AuthenticationManager authenticationManager) {
        AuthenticationWebFilter basicAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);
        basicAuthenticationFilter.setServerAuthenticationConverter(new BaicAuthenticationConverter(new BasicHandler()));
        basicAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

        return basicAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new NoOpPasswordEncoder();
    }

    @Bean
    public ServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }
}
