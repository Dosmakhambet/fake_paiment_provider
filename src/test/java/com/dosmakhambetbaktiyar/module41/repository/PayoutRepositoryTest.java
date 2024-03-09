package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.config.PostgreContainerConfiguration;
import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.Merchant;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import com.dosmakhambetbaktiyar.module41.model.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
class PayoutRepositoryTest extends PostgreContainerConfiguration {

    @Autowired
    private PayoutRepository repository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    public void createPayoutTest() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Payout payout = createPayout();

        Mono<Payout> payoutMono = merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                payout.setWalletId(savedWallet.getId());
                                return repository.save(payout);
                            });
                });
        StepVerifier.create(payoutMono)
                .assertNext(savedPayout -> {
                    assertNotNull(savedPayout.getPayoutId());
                    assertEquals(payout.getAmount(), savedPayout.getAmount());
                    assertEquals(payout.getStatus(), savedPayout.getStatus());
                    assertEquals(payout.getCurrency(), savedPayout.getCurrency());
                    assertEquals(payout.getLanguage(), savedPayout.getLanguage());
                    assertEquals(payout.getNotificationUrl(), savedPayout.getNotificationUrl());
                    assertEquals(payout.getMessage(), savedPayout.getMessage());
                })
                .verifyComplete();
    }

    @Test
    public void deletePayoutTest() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Payout payout = createPayout();

        Mono<Void> deleted = merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                payout.setWalletId(savedWallet.getId());
                                return repository.save(payout)
                                        .flatMap(savedPayout -> repository.delete(savedPayout));
                            });
                });
        StepVerifier.create(deleted)
                .verifyComplete();
    }

    @Test
    public void updatePayoutTest() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Payout payout = createPayout();

        Mono<Payout> updated = merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                payout.setWalletId(savedWallet.getId());
                                return repository.save(payout)
                                        .flatMap(savedPayout -> {
                                            savedPayout.setAmount(200.0);
                                            return repository.save(savedPayout);
                                        });
                            });
                });

        StepVerifier.create(updated)
                .assertNext(savedPayout -> {
                    assertNotNull(savedPayout.getPayoutId());
                    assertEquals(200.0, savedPayout.getAmount());
                    assertEquals(payout.getStatus(), savedPayout.getStatus());
                    assertEquals(payout.getCurrency(), savedPayout.getCurrency());
                    assertEquals(payout.getLanguage(), savedPayout.getLanguage());
                    assertEquals(payout.getNotificationUrl(), savedPayout.getNotificationUrl());
                    assertEquals(payout.getMessage(), savedPayout.getMessage());
                })
                .verifyComplete();
    }

    @Test
    public void findByWalletId() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Payout payout = createPayout();

        Mono<Payout> payoutMono = merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                payout.setWalletId(savedWallet.getId());
                                return repository.save(payout);
                            });
                });

        StepVerifier.create(payoutMono)
                .assertNext(savedPayout -> {
                    assertNotNull(savedPayout.getPayoutId());
                    assertEquals(payout.getAmount(), savedPayout.getAmount());
                    assertEquals(payout.getStatus(), savedPayout.getStatus());
                    assertEquals(payout.getCurrency(), savedPayout.getCurrency());
                    assertEquals(payout.getLanguage(), savedPayout.getLanguage());
                    assertEquals(payout.getNotificationUrl(), savedPayout.getNotificationUrl());
                    assertEquals(payout.getMessage(), savedPayout.getMessage());
                })
                .verifyComplete();

        StepVerifier.create(repository.findByWalletId(wallet.getId()))
                .assertNext(payout1 -> {
                    assertNotNull(payout1.getPayoutId());
                    assertEquals(payout.getAmount(), payout1.getAmount());
                    assertEquals(payout.getStatus(), payout1.getStatus());
                    assertEquals(payout.getCurrency(), payout1.getCurrency());
                    assertEquals(payout.getLanguage(), payout1.getLanguage());
                    assertEquals(payout.getNotificationUrl(), payout1.getNotificationUrl());
                    assertEquals(payout.getMessage(), payout1.getMessage());
                })
                .verifyComplete();
    }

    @Test
    void findAllByStatus() {
        Wallet wallet = createWallet();
        Merchant merchant = createMerchant();
        Payout payout = createPayout();

        payout.setStatus(PayoutStatus.SUCCESS);
        Mono<Payout> payoutMono = merchantRepository.save(merchant)
                .flatMap(savedMerchant -> {
                    wallet.setMerchantId(savedMerchant.getMerchantId());
                    return walletRepository.save(wallet)
                            .flatMap(savedWallet -> {
                                payout.setWalletId(savedWallet.getId());
                                return repository.save(payout);
                            });
                });

        StepVerifier.create(payoutMono)
                .assertNext(savedPayout -> {
                    assertNotNull(savedPayout.getPayoutId());
                    assertEquals(payout.getStatus(), savedPayout.getStatus());
                    assertEquals(payout.getCurrency(), savedPayout.getCurrency());
                    assertEquals(payout.getLanguage(), savedPayout.getLanguage());
                    assertEquals(payout.getNotificationUrl(), savedPayout.getNotificationUrl());
                    assertEquals(payout.getMessage(), savedPayout.getMessage());
                })
                .verifyComplete();

        StepVerifier.create(repository.findAllByStatus(PayoutStatus.SUCCESS))
                .assertNext(payout1 -> {
                    assertNotNull(payout1.getPayoutId());
                    assertEquals(PayoutStatus.SUCCESS, payout1.getStatus());
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

    private Payout createPayout() {
        return Payout.builder()
                .amount(100.0)
                .status(PayoutStatus.IN_PROGRESS)
                .currency(Currency.KZT)
                .language(Language.KAZ)
                .notificationUrl("http://localhost:8080")
                .message("test message")
                .build();
    }
}