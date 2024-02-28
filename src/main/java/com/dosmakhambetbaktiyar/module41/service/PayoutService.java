package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface PayoutService extends GenericService<Payout, UUID>{

    Flux<Payout> findByStatus(PayoutStatus status);
}
