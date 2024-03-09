package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {

    Flux<Transaction> findByWalletId(Long walletId);

    Flux<Transaction> findByStatus(TransactionStatus status);

    @Query("SELECT * FROM transaction WHERE created_at >= :from AND created_at <= :to")
    Flux<Transaction> findAllFilter(LocalDateTime from, LocalDateTime to);
}
