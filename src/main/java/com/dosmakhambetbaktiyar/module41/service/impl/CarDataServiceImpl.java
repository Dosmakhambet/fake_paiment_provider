package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.CartData;
import com.dosmakhambetbaktiyar.module41.repository.CartDataRepository;
import com.dosmakhambetbaktiyar.module41.service.CartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarDataServiceImpl implements CartDataService {
    @Autowired
    private  CartDataRepository repository;
    @Override
    public Mono<CartData> save(CartData cartData) {
        if(cartData.getId() != null){
            return Mono.error(new RuntimeException("Cart data id should be null for saving new cart data."));
        }

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
    public Mono<CartData> update(CartData cartData) {
        if(cartData.getId() == null){
            return Mono.error(new RuntimeException("Cart data id should not be null for updating cart data."));
        }

        return repository.save(cartData)
                .onErrorMap(e -> new RuntimeException("Error occurred while updating cart data"));
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return repository.deleteById(aLong);
    }


    @Override
    public void delete(CartData cartData) {
        if(cartData.getId() == null){
            throw new RuntimeException("Cart data id should not be null for deleting cart data.");
        }

        repository.delete(cartData);
    }
}
