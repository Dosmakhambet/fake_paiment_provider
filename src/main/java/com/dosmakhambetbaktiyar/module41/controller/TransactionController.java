package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.TransactionDto;
import com.dosmakhambetbaktiyar.module41.dto.TransactionResponseDto;
import com.dosmakhambetbaktiyar.module41.mapper.TransactionMapper;
import com.dosmakhambetbaktiyar.module41.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/transaction/")
public class TransactionController {

    private final TransactionService service;

    private final TransactionMapper mapper;

    @GetMapping("/list")
    public Flux<TransactionDto> getAllTransactions(){
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/{id}/details")
    public Mono<TransactionDto> getTransactionById(@PathVariable("id") UUID id){
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody TransactionDto transactionDto){
        return service.save(mapper.toEntity(transactionDto)).map(mapper::toResponseDto);
    }

    @PutMapping("/{id}")
    public Mono<TransactionResponseDto> updateTransaction(@RequestBody TransactionDto transactionDto){
        return service.update(mapper.toEntity(transactionDto)).map(mapper::toResponseDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTransaction(@PathVariable("id") UUID id){
        return service.deleteById(id);
    }

}
