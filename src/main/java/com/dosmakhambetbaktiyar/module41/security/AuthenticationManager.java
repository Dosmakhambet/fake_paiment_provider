package com.dosmakhambetbaktiyar.module41.security;

import com.dosmakhambetbaktiyar.module41.service.MerchantService;
import com.dosmakhambetbaktiyar.module41.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final MerchantService merchantService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return merchantService.findById(principal.getName())
                .map(merchant -> {
                    if (merchant.getSecretKey().equals(principal.getSecretKey())) {
                        return authentication;
                    } else {
                        throw new RuntimeException("Invalid secret key");
                    }
                });
    }
}
