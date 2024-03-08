package com.dosmakhambetbaktiyar.module41.cron;


import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.CallBack;
import com.dosmakhambetbaktiyar.module41.repository.WalletRepository;
import com.dosmakhambetbaktiyar.module41.service.CallBackService;
import com.dosmakhambetbaktiyar.module41.service.PayoutService;
import com.dosmakhambetbaktiyar.module41.service.impl.ExternalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Service
public class PayoutCronJob {
    private final PayoutService payoutService;
    private final CallBackService callBackService;
    private final WalletRepository walletRepository;
    private final ObjectMapper objectMapper;
    private final ExternalService externalService;

    @Value("${base.url}")
    private String BASE_URL;

    //TODO: add remove subscription
    @Scheduled(cron = "0/30 * * * * *")
    public void sendPayout() {
        log.info("Payout cron job");
        payoutService.findByStatus(PayoutStatus.IN_PROGRESS)
                .flatMap(payout -> {
                    int random = (int) (Math.random() * 10);
                    log.info("Random: " + random + " for payout: " + payout.getPayoutId());

                    if (random > 3) {
                        return walletRepository.findForUpdateById(payout.getWalletId())
                                .flatMap(wallet -> {
                                    log.info("Wallet balance: " + wallet.getBalance() + " for payout balance " + payout.getAmount() + " -> id :" + payout.getPayoutId());

                                    if (wallet.getBalance() >= payout.getAmount()) {
                                        payout.setStatus(PayoutStatus.SUCCESS);
                                        payout.setMessage("Payout is successfuly completed");
                                        wallet.setBalance(wallet.getBalance() - payout.getAmount());

                                        return externalService.sendPostToExternalService("/payout", payout)
                                                .doOnNext(responseBody -> {
                                                    try {
                                                        String id = objectMapper.readTree(responseBody).get("id").asText();
                                                        payout.setNotificationUrl(BASE_URL + "/payout/" + id);
                                                    } catch (JsonProcessingException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                })
                                                .then(Mono.zip(payoutService.update(payout), walletRepository.save(wallet)))
                                                .doOnSuccess(tuple -> log.info("Payout success " + tuple.getT1().getPayoutId()));
                                    } else {
                                        payout.setStatus(PayoutStatus.FAILED);
                                        payout.setMessage("PAYOUT_MIN_AMOUNT");

                                        return externalService.sendPostToExternalService("/payout", payout)
                                                .doOnNext(responseBody -> {
                                                    try {
                                                        String id = objectMapper.readTree(responseBody).get("id").asText();
                                                        payout.setNotificationUrl(BASE_URL + "/payout/" + id);
                                                    } catch (JsonProcessingException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                })
                                                .then(payoutService.update(payout));
                                    }
                                });
                    } else {
                        return Mono.error(() -> new RuntimeException("Payout failed"))
                                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5))
                                        .doAfterRetry(retrySignal -> {
                                            log.info("Retrying at " + LocalDateTime.now() + " for payout " + payout.getPayoutId());
                                            CallBack callBack = CallBack.builder()
                                                    .status(PayoutStatus.FAILED)
                                                    .payoutId(payout.getPayoutId())
                                                    .createdAt(LocalDateTime.now())
                                                    .build();
                                            callBackService.save(callBack).subscribe();
                                        })
                                )
                                .doOnError(throwable -> {
                                    payout.setStatus(PayoutStatus.FAILED);
                                    payout.setMessage("PAYOUT_MIN_AMOUNT");
                                    payoutService.update(payout).subscribe();
                                });
                    }
                })
                .subscribe();
    }
}
