package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.WalletDto;
import com.dosmakhambetbaktiyar.module41.mapper.WalletMapper;
import com.dosmakhambetbaktiyar.module41.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService service;
    private final WalletMapper mapper;

    @GetMapping("")
    public Flux<WalletDto> getAllWallets() {
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/:id")
    public Mono<WalletDto> getWalletById(@PathVariable Long id) {
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping("")
    public Mono<WalletDto> createWallet(@RequestBody WalletDto walletDto) {
        return service.save(mapper.toEntity(walletDto)).map(mapper::toDto);
    }

    @PutMapping("/:id")
    public Mono<WalletDto> updateWallet(@PathVariable Long id,@RequestBody WalletDto walletDto) {
        return service.update(mapper.toEntity(walletDto)).map(mapper::toDto);
    }

    @DeleteMapping("/:id")
    public Mono<Void> deleteWallet(@PathVariable Long id) {
        return service.deleteById(id);
    }
}
