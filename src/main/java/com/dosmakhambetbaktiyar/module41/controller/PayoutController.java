package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.PayoutDto;
import com.dosmakhambetbaktiyar.module41.dto.PayoutResponseDto;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.mapper.PayoutMapper;
import com.dosmakhambetbaktiyar.module41.mapper.PayoutResponseMapper;
import com.dosmakhambetbaktiyar.module41.service.PayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments/payout")
public class PayoutController {

    @Autowired
    private PayoutService service;

    @Autowired
    private PayoutMapper mapper;

    @Autowired
    private PayoutResponseMapper responseMapper;
    @GetMapping("/list")
    public Flux<PayoutDto> getAllPayments(){
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/{id}/details")
    public Mono<PayoutResponseDto> getPaymentById(@PathVariable("id") UUID id){
        return service.findById(id).map(responseMapper::toResponseDto);
    }

    //TODO: не понял call back url
    @PostMapping("/")
    public Mono<PayoutResponseDto> createPayment(@RequestBody PayoutDto paymentDto){
        return service.save(mapper.toEntity(paymentDto)).map(mapper::toResponseDto);
    }

    @PutMapping("/{id}")
    public Mono<PayoutResponseDto> updatePayment(@RequestBody PayoutDto paymentDto){
        return service.update(mapper.toEntity(paymentDto)).map(mapper::toResponseDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePayment(@PathVariable("id") UUID id){
        return service.deleteById(id);
    }

    @GetMapping("/status/{status}")
    public Flux<PayoutDto> getPaymentsByStatus(@PathVariable("status") PayoutStatus status){
        return service.findByStatus(status).map(mapper::toDto);
    }

}
