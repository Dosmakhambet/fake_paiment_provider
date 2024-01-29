package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.model.Customer;
import com.dosmakhambetbaktiyar.module41.repository.CustomerRepository;
import com.dosmakhambetbaktiyar.module41.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public Mono<Customer> save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Mono<Customer> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        repository.deleteById(aLong);
        return null;
    }

    @Override
    public void delete(Customer customer) {
        repository.delete(customer);
    }
}
