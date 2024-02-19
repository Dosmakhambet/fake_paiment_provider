package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import com.dosmakhambetbaktiyar.module41.repository.CartDataRepository;
import com.dosmakhambetbaktiyar.module41.repository.TransactionRepository;
import com.dosmakhambetbaktiyar.module41.repository.WalletRepository;
import com.dosmakhambetbaktiyar.module41.service.CustomerService;
import com.dosmakhambetbaktiyar.module41.service.MerchantService;
import com.dosmakhambetbaktiyar.module41.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final CartDataRepository cartDataRepository;
    private final CustomerService customerService;
    private final WalletRepository walletRepository;
    private final MerchantService merchantService;

    @Override
    public Mono<Transaction> save(Transaction transaction) {

        return merchantService.getMerchantId()
                .flatMap(merchantId ->
                        walletRepository.findByMerchantIdAndCurrency(merchantId, transaction.getCurrency())
                ).flatMap(wallet -> {
                    transaction.setWalletId(wallet.getId());
                    transaction.setStatus(TransactionStatus.IN_PROGRESS);
                    return repository.save(transaction);
                });
    }

    @Override
    public Mono<Transaction> findById(UUID uuid) {

        return repository.findById(uuid)
                .flatMap(transaction -> Mono.zip(
                        cartDataRepository.findById(transaction.getCartDataId()),
                        customerService.findById(transaction.getCustomerId()),
                        walletRepository.findById(transaction.getWalletId()))
                        .map(tuples -> {
                            transaction.setCartData(tuples.getT1());
                            transaction.setCustomer(tuples.getT2());
                            transaction.setWallet(tuples.getT3());

                            return transaction;
                        })
                );
    }

    @Override
    public Flux<Transaction> findAll() {

        return repository.findAll().
                flatMap(transaction ->Mono.zip(
                                cartDataRepository.findById(transaction.getCartDataId()),
                                customerService.findById(transaction.getCustomerId()),
                                walletRepository.findById(transaction.getWalletId()))
                        .map(tuples -> {
                            transaction.setCartData(tuples.getT1());
                            transaction.setCustomer(tuples.getT2());
                            transaction.setWallet(tuples.getT3());

                            return transaction;
                        })
                );
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {

        return repository.save(transaction);
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return repository.deleteById(uuid);
    }

    @Override
    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }
}
