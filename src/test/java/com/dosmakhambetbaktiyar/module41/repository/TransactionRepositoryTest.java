package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.config.PostgreContainerConfiguration;
import com.dosmakhambetbaktiyar.module41.dto.TransactionFilterDto;
import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.model.Merchant;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import com.dosmakhambetbaktiyar.module41.model.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
class TransactionRepositoryTest extends PostgreContainerConfiguration {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    void createTransactionTest() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Transaction transaction = createTransaction();

        Mono<Transaction> transactionMono = merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                transaction.setWalletId(savedWallet.getId());
                                return repository.save(transaction);
                            });
                });

        StepVerifier.create(transactionMono)
                .assertNext(savedTransaction -> {
                    assertNotNull(savedTransaction.getTransactionId());
                    assertEquals(transaction.getAmount(), savedTransaction.getAmount());
                    assertEquals(transaction.getStatus(), savedTransaction.getStatus());
                    assertEquals(transaction.getCurrency(), savedTransaction.getCurrency());
                    assertEquals(transaction.getLanguage(), savedTransaction.getLanguage());
                    assertEquals(transaction.getNotificationUrl(), savedTransaction.getNotificationUrl());
                    assertEquals(transaction.getMessage(), savedTransaction.getMessage());
                })
                .verifyComplete();
    }

    @Test
    void findByStatus() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Transaction transaction = createTransaction();
        transaction.setStatus(TransactionStatus.SUCCESS);
        merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                transaction.setWalletId(savedWallet.getId());
                                return repository.save(transaction);
                            });
                }).block();

        Flux<Transaction> transactions = repository.findByStatus(TransactionStatus.SUCCESS);

        StepVerifier.create(transactions.elementAt(0))
                .assertNext(savedTransaction -> {
                    assertNotNull(savedTransaction.getTransactionId());
                    assertEquals(transaction.getAmount(), savedTransaction.getAmount());
                    assertEquals(transaction.getStatus(), savedTransaction.getStatus());
                    assertEquals(transaction.getCurrency(), savedTransaction.getCurrency());
                    assertEquals(transaction.getLanguage(), savedTransaction.getLanguage());
                    assertEquals(transaction.getNotificationUrl(), savedTransaction.getNotificationUrl());
                    assertEquals(transaction.getMessage(), savedTransaction.getMessage());
                })
                .verifyComplete();
    }

    @Test
    void findAllFilter() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Transaction transaction = createTransaction();
        TransactionFilterDto filterDto = createFilterDto();

        merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                transaction.setWalletId(savedWallet.getId());
                                return repository.save(transaction);
                            });
                }).block();

        Flux<Transaction> transactions = repository.findAllFilter(filterDto.getStartDate(), filterDto.getEndDate());

        StepVerifier.create(transactions.elementAt(0))
                .assertNext(savedTransaction -> {
                    assertNotNull(savedTransaction.getTransactionId());
                    assertEquals(transaction.getAmount(), savedTransaction.getAmount());
                    assertEquals(transaction.getStatus(), savedTransaction.getStatus());
                    assertEquals(transaction.getCurrency(), savedTransaction.getCurrency());
                    assertEquals(transaction.getLanguage(), savedTransaction.getLanguage());
                    assertEquals(transaction.getNotificationUrl(), savedTransaction.getNotificationUrl());
                    assertEquals(transaction.getMessage(), savedTransaction.getMessage());
                })
                .verifyComplete();
    }

    private Wallet createWallet() {
        return Wallet.builder()
                .currency(Currency.KZT)
                .balance(1000.0)
                .build();
    }

    private Merchant createMerchant() {
        return Merchant.builder()
                .secretKey("test_secret")
                .build();
    }

    private Transaction createTransaction() {
        return Transaction.builder()
                .amount(100.0)
                .status(TransactionStatus.IN_PROGRESS)
                .currency(Currency.KZT)
                .language(Language.KAZ)
                .createdAt(LocalDateTime.now())
                .notificationUrl("http://localhost:8080")
                .message("test message")
                .build();
    }

    private TransactionFilterDto createFilterDto() {
        return TransactionFilterDto.builder()
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .build();
    }
}