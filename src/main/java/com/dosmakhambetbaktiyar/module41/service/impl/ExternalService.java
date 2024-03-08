package com.dosmakhambetbaktiyar.module41.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExternalService {
    private final WebClient webClient;

    public Mono<String> sendPostToExternalService(String endpoint, Object body) {
        return webClient.post()
                .uri(endpoint)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    }


}
