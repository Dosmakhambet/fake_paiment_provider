package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.model.CallBack;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CallBackRepository extends ReactiveCrudRepository<CallBack, Long>{
    Flux<CallBack> findByPayoutId(UUID payoutId);
}
