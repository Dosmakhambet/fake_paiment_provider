package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.dto.TransactionFilterDto;
import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface TransactionService extends GenericService<Transaction, UUID> {

    Flux<Transaction> findByStatus(TransactionStatus status);

    Flux<Transaction> findAllFilter(TransactionFilterDto filterDto);
}
