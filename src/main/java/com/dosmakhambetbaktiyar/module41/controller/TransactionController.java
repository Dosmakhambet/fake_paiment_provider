package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.TransactionDto;
import com.dosmakhambetbaktiyar.module41.mapper.TransactionMapper;
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

    @Autowired
    private TransactionMapper mapper;

    @GetMapping("")
    public Flux<TransactionDto> getAllTransactions(){
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<TransactionDto> getTransactionById(@PathVariable("id") UUID id){
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping
    public Mono<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto){
        return service.save(mapper.toEntity(transactionDto)).map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<TransactionDto> updateTransaction(@RequestBody TransactionDto transactionDto){
        return service.update(mapper.toEntity(transactionDto)).map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTransaction(@PathVariable("id") UUID id){
        return service.deleteById(id);
    }

}
