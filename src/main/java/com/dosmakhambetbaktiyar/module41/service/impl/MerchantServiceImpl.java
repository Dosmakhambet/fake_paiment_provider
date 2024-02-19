package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.Merchant;
import com.dosmakhambetbaktiyar.module41.repository.MerchantRepository;
import com.dosmakhambetbaktiyar.module41.security.CustomPrincipal;
import com.dosmakhambetbaktiyar.module41.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository repository;
    @Override
    public Mono<Merchant> save(Merchant merchant) {
        return repository.save(merchant);
    }

    @Override
    public Mono<Merchant> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Flux<Merchant> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Merchant> update(Merchant merchant) {
        return repository.save(merchant);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return repository.deleteById(s);
    }

    @Override
    public void delete(Merchant merchant) {
        repository.delete(merchant);
    }

    @Override
    public Mono<String> getMerchantId(){
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(CustomPrincipal.class)
                .map(CustomPrincipal::getName);
    }


}
