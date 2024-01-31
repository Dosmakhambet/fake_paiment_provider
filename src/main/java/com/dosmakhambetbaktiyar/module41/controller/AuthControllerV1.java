package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.AuthRequestDto;
import com.dosmakhambetbaktiyar.module41.dto.AuthResponseDto;
import com.dosmakhambetbaktiyar.module41.dto.UserDto;
import com.dosmakhambetbaktiyar.module41.mapper.UserMapper;
import com.dosmakhambetbaktiyar.module41.security.CustomPrincipal;
import com.dosmakhambetbaktiyar.module41.security.SecurityService;
import com.dosmakhambetbaktiyar.module41.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto){
        return userService.register(userMapper.toEntity(userDto))
                .map(userMapper::toDto);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){
       return securityService.autenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
               .flatMap(tokenDetails -> Mono.just(
                          AuthResponseDto.builder()
                                 .token(tokenDetails.getToken())
                                 .issuedAt(tokenDetails.getIssuedAt())
                                 .expiresAt(tokenDetails.getExpiresAt())
                                 .build()
               ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        return userService.findById(principal.getId())
                .map(userMapper::toDto);
    }
}
