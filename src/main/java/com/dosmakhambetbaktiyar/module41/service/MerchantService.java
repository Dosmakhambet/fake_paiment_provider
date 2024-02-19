package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.model.Merchant;
import reactor.core.publisher.Mono;

public interface MerchantService extends GenericService<Merchant, String>{
    Mono<String> getMerchantId();
}
