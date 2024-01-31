package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.model.User;
import reactor.core.publisher.Mono;

public interface UserService extends GenericService<User, Long>{
    Mono<User> register(User user);
    Mono<User> findByUsername(String username);

}
