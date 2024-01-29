package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.TransactionDto;
import com.dosmakhambetbaktiyar.module41.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("")
    public Flux<TransactionDto> getAllTransactions(){
        return service.findAll().map(TransactionDto::toDto);
    }

    @GetMapping("/{id}")
    public Mono<TransactionDto> getTransactionById(@PathVariable("id") UUID id){
        return service.findById(id).map(TransactionDto::toDto);
    }

    @PostMapping
    public Mono<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto){
        return service.save(transactionDto.toEntity()).map(TransactionDto::toDto);
    }

    @PutMapping("/{id}")
    public Mono<TransactionDto> updateTransaction(@RequestBody TransactionDto transactionDto){
        return service.update(transactionDto.toEntity()).map(TransactionDto::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTransaction(@PathVariable("id") UUID id){
        return service.deleteById(id);
    }

}
