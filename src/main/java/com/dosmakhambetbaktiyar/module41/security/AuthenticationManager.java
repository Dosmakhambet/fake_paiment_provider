package com.dosmakhambetbaktiyar.module41.security;

import com.dosmakhambetbaktiyar.module41.repository.UserRepository;
import com.dosmakhambetbaktiyar.module41.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.findById(principal.getId())
                .map(user -> authentication);
    }
}
