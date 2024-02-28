package com.dosmakhambetbaktiyar.module41.cron;

import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.repository.WalletRepository;
import com.dosmakhambetbaktiyar.module41.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@EnableScheduling
public class TransactionCronJob {
    private final TransactionService transactionService;
    private final WalletRepository walletRepository;


    public void sendTransaction() {
        transactionService.findByStatus(TransactionStatus.IN_PROGRESS)
                .map(transaction -> {
                    transaction.setStatus(TransactionStatus.SUCCESS);

                    return walletRepository.findForUpdateById(transaction.getWalletId())
                            .map(wallet -> {
                                wallet.setBalance(wallet.getBalance() + transaction.getAmount());
                                return Mono.zip(
                                        transactionService.update(transaction),
                                        walletRepository.save(wallet)
                                );
                            });
                });
    }

}
