package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.MerchantDto;
import com.dosmakhambetbaktiyar.module41.mapper.MerchantMapper;
import com.dosmakhambetbaktiyar.module41.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class MerchantController {
    private final MerchantService service;
    private final MerchantMapper mapper;

    @GetMapping("")
    public Flux<MerchantDto> getAllMerchants() {
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/:id")
    public Mono<MerchantDto> getMerchantById(@PathVariable String id) {
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MerchantDto> createMerchant(@RequestBody MerchantDto merchantDto) {
        return service.save(mapper.toEntity(merchantDto)).map(mapper::toDto);
    }

    @PutMapping("/:id")
    public Mono<MerchantDto> updateMerchant(@PathVariable String id, @RequestBody MerchantDto merchantDto) {
        return service.update(mapper.toEntity(merchantDto)).map(mapper::toDto);
    }

    @DeleteMapping("/:id")
    public Mono<Void> deleteMerchant(@PathVariable String id) {
        return service.deleteById(id);
    }
}
