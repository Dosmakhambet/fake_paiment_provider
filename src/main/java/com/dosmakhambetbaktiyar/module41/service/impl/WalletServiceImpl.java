package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.model.Wallet;
import com.dosmakhambetbaktiyar.module41.repository.PayoutRepository;
import com.dosmakhambetbaktiyar.module41.repository.TransactionRepository;
import com.dosmakhambetbaktiyar.module41.repository.WalletRepository;
import com.dosmakhambetbaktiyar.module41.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;
    private final TransactionRepository transactionRepository;
    private final PayoutRepository payoutRepository;
    @Override
    public Mono<Wallet> save(Wallet wallet) {
        return repository.save(wallet);
    }

    @Override
    public Mono<Wallet> findById(Long aLong) {
        return repository.findById(aLong)
                .flatMap(wallet -> Mono.zip(
                        transactionRepository.findByWalletId(wallet.getId()).collectList(),
                        payoutRepository.findByWalletId(wallet.getId()).collectList())
                        .map(tuple -> {
                            wallet.setTransactions(tuple.getT1());
                            wallet.setPayouts(tuple.getT2());
                            return wallet;
                        })
                );
    }

    @Override
    public Flux<Wallet> findAll() {
        return repository.findAll()
                .flatMap(wallet -> Mono.zip(
                                transactionRepository.findByWalletId(wallet.getId()).collectList(),
                                payoutRepository.findByWalletId(wallet.getId()).collectList())
                        .map(tuple -> {
                            wallet.setTransactions(tuple.getT1());
                            wallet.setPayouts(tuple.getT2());
                            return wallet;
                        })
                );
    }

    @Override
    public Mono<Wallet> update(Wallet wallet) {
        return repository.save(wallet);
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return repository.deleteById(aLong);
    }

    @Override
    public void delete(Wallet wallet) {
        repository.delete(wallet);
    }

    @Override
    public Mono<Wallet> findByMerchantIdAndCurrency(String merchantId, Currency currency){
        return repository.findByMerchantIdAndCurrency(merchantId, currency);
    }

}
