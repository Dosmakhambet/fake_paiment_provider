package com.dosmakhambetbaktiyar.module41.cron;

import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.repository.WalletRepository;
import com.dosmakhambetbaktiyar.module41.service.TransactionService;
import com.dosmakhambetbaktiyar.module41.service.impl.ExternalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class TransactionCronJob {
    private final TransactionService transactionService;
    private final WalletRepository walletRepository;
    private final ObjectMapper objectMapper;
    private final ExternalService externalService;

    @Value("${base.url}")
    private String BASE_URL;

    public void sendTransaction() {
        log.info("Payout cron job");
        transactionService.findByStatus(TransactionStatus.IN_PROGRESS)
                .flatMap(transaction -> {
                    int random = (int) (Math.random() * 10);
                    log.info("Random: " + random + " for transaction: " + transaction.getTransactionId());

                    return walletRepository.findForUpdateById(transaction.getWalletId())
                            .flatMap(wallet -> {
                                log.info("Wallet balance: " + wallet.getBalance() + " for payout balance " + transaction.getAmount() + " -> id :" + transaction.getTransactionId());

                                if (wallet.getBalance() >= transaction.getAmount()) {
                                    transaction.setStatus(TransactionStatus.SUCCESS);
                                    transaction.setMessage("Payout is successfuly completed");
                                    wallet.setBalance(wallet.getBalance() - transaction.getAmount());

                                    return externalService.sendPostToExternalService("/transaction", transaction)
                                            .doOnNext(responseBody -> {
                                                try {
                                                    String id = objectMapper.readTree(responseBody).get("id").asText();
                                                    transaction.setNotificationUrl(BASE_URL + "/transaction/" + id);
                                                } catch (JsonProcessingException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            })
                                            .then(Mono.zip(transactionService.update(transaction), walletRepository.save(wallet)))
                                            .doOnSuccess(tuple -> log.info("Transaction success " + tuple.getT1().getTransactionId()));
                                } else {
                                    transaction.setStatus(TransactionStatus.FAILED);
                                    transaction.setMessage("PAYOUT_MIN_AMOUNT");

                                    return externalService.sendPostToExternalService("/transaction", transaction)
                                            .doOnNext(responseBody -> {
                                                try {
                                                    String id = objectMapper.readTree(responseBody).get("id").asText();
                                                    transaction.setNotificationUrl(BASE_URL + "/transaction/" + id);
                                                } catch (JsonProcessingException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            })
                                            .then(transactionService.update(transaction));
                                }
                            });
                })
                .subscribe();
    }

}
