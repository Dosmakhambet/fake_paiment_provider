package com.dosmakhambetbaktiyar.module41.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService <T,ID> {
    Mono<T> save(T t);
    Mono<T> findById(ID id);
    Flux<T> findAll();
    Mono<T> update(T t);
    Mono<Void> deleteById(ID id);
    void delete(T t);
}
