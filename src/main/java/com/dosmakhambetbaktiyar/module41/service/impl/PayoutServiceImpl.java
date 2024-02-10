package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.CallBack;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import com.dosmakhambetbaktiyar.module41.repository.*;
import com.dosmakhambetbaktiyar.module41.service.PayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {


    private final PayoutRepository repository;
    private final CallBackRepository callBackRepository;
    private final CartDataRepository cartDataRepository;
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;



    @Override
    public Mono<Payout> save(Payout payout) {

        return repository.save(payout)
                .doOnError(throwable -> {
                    System.out.println("Error occurred while saving payout: " + throwable.getMessage());
                    callBackRepository.save(CallBack.builder()
                            .status(payout.getStatus())
                            .build())
                            .subscribe(callBack -> System.out.println("Callback saved: " + callBack.getId()));
                })
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(3)));
    }

    @Override
    public Mono<Payout> findById(UUID uuid) {
        return repository.findById(uuid);

//        return Mono.zip(repository.findById(uuid))
//                .map(tuple -> {
//                    Payout payout = tuple.getT1();
//                    return payout;
//                }).flatMap(payout -> Mono.zip(
//                                cartDataRepository.findById(payout.getCartDataId()),
//                                customerRepository.findById(payout.getCustomerId()),
//                                walletRepository.findById(payout.getWalletId()))
//                        .map(tuple -> {
//                            payout.setCartData(tuple.getT1());
//                            payout.setCustomer(tuple.getT2());
//                            payout.setWallet(tuple.getT3());
//                            return payout;
//                        }));
    }

    @Override
    public Flux<Payout> findAll() {
        return repository.findAll();
//                .flatMap(payout -> Mono.zip(
//                        callBackRepository.findByPayoutId(payout.getPayoutId()).or(Flux.empty()).collectList(),
//                        cartDataRepository.findById(payout.getCartDataId()),
//                        customerRepository.findById(payout.getCustomerId()),
//                        walletRepository.findById(payout.getWalletId()))
//                        .map(tuple -> {
//                            payout.setCartData(tuple.getT2());
//                            payout.setCustomer(tuple.getT3());
//                            payout.setWallet(tuple.getT4());
//                            return payout;
//                        })
//                );
    }

    @Override
    public Mono<Payout> update(Payout payout) {
        return repository.save(payout);
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        repository.deleteById(uuid);
        return null;
    }

    @Override
    public void delete(Payout payout) {
        repository.delete(payout);
    }
}
