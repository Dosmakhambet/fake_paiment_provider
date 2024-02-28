package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import com.dosmakhambetbaktiyar.module41.repository.*;
import com.dosmakhambetbaktiyar.module41.service.MerchantService;
import com.dosmakhambetbaktiyar.module41.service.PayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {


    private final PayoutRepository repository;
    private final CallBackRepository callBackRepository;
    private final CartDataRepository cartDataRepository;
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;
    private final MerchantService merchantService;

    @Override
    public Mono<Payout> save(Payout payout) {
        return merchantService.getMerchantId()
                .flatMap(merchantId ->
                        Mono.zip(
                                walletRepository.findByMerchantIdAndCurrency(merchantId, payout.getCurrency()),
                                cartDataRepository.findByCartNumber(payout.getCartData().getCartNumber())
                                )
                                .flatMap(tuple -> {
                                    payout.setWalletId(tuple.getT1().getId());
                                    payout.setCartDataId(tuple.getT2().getId());
                                    payout.setCustomerId(payout.getCustomer().getId());
                                    payout.setStatus(PayoutStatus.IN_PROGRESS);
                                    payout.setMessage("OK");
                                    return repository.save(payout);
                                })

                );
    }

    @Override
    public Mono<Payout> findById(UUID uuid) {
        return Mono.zip(repository.findById(uuid), callBackRepository.findByPayoutId(uuid).or(Flux.empty()).collectList())
                .map(tuple -> {
                    Payout payout = tuple.getT1();
                    payout.setCallBacks(tuple.getT2());
                    return payout;
                }).flatMap(payout -> Mono.zip(
                                cartDataRepository.findById(payout.getCartDataId()),
                                customerRepository.findById(payout.getCustomerId()),
                                walletRepository.findById(payout.getWalletId()))
                        .map(tuple -> {
                            payout.setCartData(tuple.getT1());
                            payout.setCustomer(tuple.getT2());
                            payout.setWallet(tuple.getT3());
                            return payout;
                        }));
    }

    @Override
    public Flux<Payout> findAll() {
        return repository.findAll()
                .flatMap(payout -> Mono.zip(
                                callBackRepository.findByPayoutId(payout.getPayoutId()).or(Flux.empty()).collectList(),
                                cartDataRepository.findById(payout.getCartDataId()),
                                customerRepository.findById(payout.getCustomerId()),
                                walletRepository.findById(payout.getWalletId()))
                        .map(tuple -> {
                            payout.setCartData(tuple.getT2());
                            payout.setCustomer(tuple.getT3());
                            payout.setWallet(tuple.getT4());
                            return payout;
                        })
                );
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

    @Override
    public Flux<Payout> findByStatus(PayoutStatus status) {
        return repository.findAllByStatus(status);
    }

}
