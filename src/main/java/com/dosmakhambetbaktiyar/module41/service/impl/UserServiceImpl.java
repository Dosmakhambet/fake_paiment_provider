package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.enums.UserRole;
import com.dosmakhambetbaktiyar.module41.model.User;
import com.dosmakhambetbaktiyar.module41.repository.UserRepository;
import com.dosmakhambetbaktiyar.module41.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> save(User user) {
        return null;
    }

    @Override
    public Mono<User> findById(Long aLong) {
        return null;
    }

    @Override
    public Flux<User> findAll() {
        return null;
    }

    @Override
    public Mono<User> update(User user) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    public Mono<User> register(User user){
        return repository.save(
                User.builder()
                        .username(user.getUsername())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(UserRole.USER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> log.info("User {} registered", u.getUsername()));
    }

    public Mono<User> findByUsername(String username){
        return repository.findByUsername(username);
    }

}
