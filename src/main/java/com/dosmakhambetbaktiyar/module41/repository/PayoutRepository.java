package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface PayoutRepository extends ReactiveCrudRepository<Payout, UUID> {

    Flux<Payout> findByWalletId(Long walletId);

    Flux<Payout> findAllByStatus(PayoutStatus status);
}
