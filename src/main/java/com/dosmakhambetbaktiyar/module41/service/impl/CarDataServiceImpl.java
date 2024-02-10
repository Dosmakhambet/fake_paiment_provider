package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.CartData;
import com.dosmakhambetbaktiyar.module41.repository.CartDataRepository;
import com.dosmakhambetbaktiyar.module41.security.CustomPrincipal;
import com.dosmakhambetbaktiyar.module41.service.CartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarDataServiceImpl implements CartDataService {
    @Autowired
    private  CartDataRepository repository;
    @Override
    public Mono<CartData> save(CartData cartData) {
        return repository.save(cartData)
                .onErrorMap(e -> new RuntimeException("Error occurred while saving cart data " + e.getMessage()));
    }

    @Override
    public Mono<CartData> findById(Long aLong) {
        return repository.findById(aLong)
                .onErrorMap(e -> new RuntimeException("Error occurred while finding cart data"));
    }


    @Override
    public Flux<CartData> findAll() {

        return repository.findAll();
    }

    @Override
    public Mono<String> findAll2() {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(CustomPrincipal.class) // Replace with your principal class
                .map(CustomPrincipal::getName) // Replace with your method to get user id
                .flatMap(userId -> {
                    // Use userId here
                    System.out.println("User ID: " + userId);
                    return Mono.just(userId);
                });
    }

    @Override
    public Mono<CartData> update(CartData cartData) {
        return repository.save(cartData)
                .onErrorMap(e -> new RuntimeException("Error occurred while updating cart data"));
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return repository.deleteById(aLong);
    }


    @Override
    public void delete(CartData cartData) {
        repository.delete(cartData);
    }
}
