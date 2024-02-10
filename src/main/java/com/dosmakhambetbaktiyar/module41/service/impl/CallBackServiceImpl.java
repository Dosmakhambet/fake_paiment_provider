package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.CallBack;
import com.dosmakhambetbaktiyar.module41.repository.CallBackRepository;
import com.dosmakhambetbaktiyar.module41.repository.PayoutRepository;
import com.dosmakhambetbaktiyar.module41.service.CallBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CallBackServiceImpl implements CallBackService {

    private final CallBackRepository repository;
    private final PayoutRepository payoutRepository;
    @Override
    public Mono<CallBack> save(CallBack callBack) {
        return repository.save(callBack);
    }

    @Override
    public Mono<CallBack> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public Flux<CallBack> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CallBack> update(CallBack callBack) {
        return repository.save(callBack);
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return repository.deleteById(aLong);
    }

    @Override
    public void delete(CallBack callBack) {
        repository.delete(callBack);
    }
}
