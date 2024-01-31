package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.CustomerDto;
import com.dosmakhambetbaktiyar.module41.mapper.CustomerMapper;
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

    @Autowired
    private CustomerMapper mapper;

    @GetMapping("")
    public Flux<CustomerDto> getAllCustomers(){
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable("id") Long id){
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping("")
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        return service.save(mapper.toEntity(customerDto)).map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto){
        return service.update(mapper.toEntity(customerDto)).map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable("id") Long id){
        return service.deleteById(id);
    }
}
