package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.PayoutDto;
import com.dosmakhambetbaktiyar.module41.mapper.PayoutMapper;
import com.dosmakhambetbaktiyar.module41.service.PayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/payment")
public class PayoutController {

    @Autowired
    private PayoutService service;

    @Autowired
    private PayoutMapper mapper;
    @GetMapping("")
    public Flux<PayoutDto> getAllPayments(){
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<PayoutDto> getPaymentById(@PathVariable("id") Long id){
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping("")
    public Mono<PayoutDto> createPayment(@RequestBody PayoutDto paymentDto){
        return service.save(mapper.toEntity(paymentDto)).map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<PayoutDto> updatePayment(@RequestBody PayoutDto paymentDto){
        return service.update(mapper.toEntity(paymentDto)).map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePayment(@PathVariable("id") Long id){
        return service.deleteById(id);
    }

}
