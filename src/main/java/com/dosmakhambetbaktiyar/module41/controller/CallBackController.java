package com.dosmakhambetbaktiyar.module41.controller;

import com.dosmakhambetbaktiyar.module41.dto.CallBackDto;
import com.dosmakhambetbaktiyar.module41.mapper.CallBackMapper;
import com.dosmakhambetbaktiyar.module41.service.CallBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/callback")
public class CallBackController {
    private final CallBackService service;
    private final CallBackMapper mapper;

    @GetMapping("")
    public Flux<CallBackDto> getAllCallBacks() {
        return service.findAll().map(mapper::toDto);
    }

    @GetMapping("/:id")
    public Mono<CallBackDto> getCallBackById(@PathVariable Long id) {
        return service.findById(id).map(mapper::toDto);
    }

    @PostMapping("")
    public Mono<CallBackDto> createCallBack(@RequestBody CallBackDto callBackDto) {
        return service.save(mapper.toEntity(callBackDto)).map(mapper::toDto);
    }

    @PutMapping("/:id")
    public Mono<CallBackDto> updateCallBack(@PathVariable Long id,@RequestBody CallBackDto callBackDto) {
        return service.update(mapper.toEntity(callBackDto)).map(mapper::toDto);
    }

    @DeleteMapping
    public Mono<Void> deleteCallBack(@PathVariable Long id) {
        return service.deleteById(id);
    }
}
