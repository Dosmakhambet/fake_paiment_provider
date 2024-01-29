package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.CartDataDto;
import com.dosmakhambetbaktiyar.module41.service.CartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/car-data")
public class CarDataController {

    @Autowired
    private CartDataService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CartDataDto> createCartData(@RequestBody CartDataDto cartDataDto) {
        System.out.println(cartDataDto);
        return service.save(cartDataDto.toEntity()).map(CartDataDto::toDto);
    }

    @GetMapping("/{id}")
    public Mono<CartDataDto> getCartDataById(@PathVariable("id") Long id) {
        return service.findById(id).map(CartDataDto::toDto);
    }

    @GetMapping
    public Flux<CartDataDto> getAllCartData() {
        return service.findAll().map(CartDataDto::toDto);
    }

    @PutMapping("/{id}")
    public Mono<CartDataDto> updateCartData(@RequestBody CartDataDto cartDataDto) {
        return service.update(cartDataDto.toEntity()).map(CartDataDto::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCartData(@PathVariable("id") Long id) {
        return service.deleteById(id);
    }
}
