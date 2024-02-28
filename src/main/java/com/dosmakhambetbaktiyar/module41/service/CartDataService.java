package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.model.CartData;
import reactor.core.publisher.Mono;

public interface CartDataService extends GenericService<CartData, Long>{
    Mono<String> findAll2();

    Mono<CartData> findByCartNumber(String cartNumber);
}
