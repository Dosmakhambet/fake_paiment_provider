package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.model.CartData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CartDataRepository extends ReactiveCrudRepository<CartData, Long> {

    Mono<CartData> findByCartNumber(String cartNumber);
}
