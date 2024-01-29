package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.CustomerDto;
import com.dosmakhambetbaktiyar.module41.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("")
    public Flux<CustomerDto> getAllCustomers(){
        return service.findAll().map(CustomerDto::toDto);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable("id") Long id){
        return service.findById(id).map(CustomerDto::toDto);
    }

    @PostMapping("")
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        return service.save(customerDto.toEntity()).map(CustomerDto::toDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto){
        return service.update(customerDto.toEntity()).map(CustomerDto::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable("id") Long id){
        return service.deleteById(id);
    }
}
