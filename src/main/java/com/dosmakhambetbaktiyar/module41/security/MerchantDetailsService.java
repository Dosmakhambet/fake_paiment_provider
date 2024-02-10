package com.dosmakhambetbaktiyar.module41.security;

import com.dosmakhambetbaktiyar.module41.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MerchantDetailsService implements ReactiveUserDetailsService {

    private final MerchantService merchantService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return merchantService.findById(username)
                .map(merchant -> User
                        .withUsername(merchant.getMerchantId())
                        .password(merchant.getSecretKey())
                        .roles("MERCHANT")
                        .build())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }
}
