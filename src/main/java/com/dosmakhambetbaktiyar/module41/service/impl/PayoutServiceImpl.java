package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.Payout;
import com.dosmakhambetbaktiyar.module41.repository.PayoutRepository;
import com.dosmakhambetbaktiyar.module41.service.PayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PayoutServiceImpl implements PayoutService {

    @Autowired
    private PayoutRepository repository;

    @Override
    public Mono<Payout> save(Payout payout) {
        return repository.save(payout);
    }

    @Override
    public Mono<Payout> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public Flux<Payout> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Payout> update(Payout payout) {
        return repository.save(payout);
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        repository.deleteById(aLong);
        return null;
    }

    @Override
    public void delete(Payout payout) {
        repository.delete(payout);
    }
}
