package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.model.Wallet;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface WalletRepository extends ReactiveCrudRepository<Wallet, Long>{

   Mono<Wallet> findByMerchantIdAndCurrency(String merchantId, Currency currency);

   @Transactional
   @Query("SELECT * FROM wallet WHERE id = :id FOR UPDATE")
   Mono<Wallet> findForUpdateById(Long id);
}
