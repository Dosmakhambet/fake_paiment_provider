package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.model.Wallet;
import reactor.core.publisher.Mono;

public interface WalletService extends GenericService<Wallet, Long>{
    Mono<Wallet> findByMerchantIdAndCurrency(String merchantId, Currency currency);
}
