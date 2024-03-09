package com.dosmakhambetbaktiyar.module41.repository;

import com.dosmakhambetbaktiyar.module41.config.PostgreContainerConfiguration;
import com.dosmakhambetbaktiyar.module41.model.CartData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
class CartDataRepositoryTest extends PostgreContainerConfiguration {

    @Autowired
    private CartDataRepository cartDataRepository;
    @Test
    public void createCartDataTest() {
        CartData cartData = createCartData1();
        Mono<CartData> saved = cartDataRepository.save(cartData);
        StepVerifier.create(saved)
                .assertNext(data -> {
                    assertNotNull(data.getId());
                    assertEquals(cartData.getCartNumber(), data.getCartNumber());
                    assertEquals(cartData.getCvv(), data.getCvv());
                    assertEquals(cartData.getExpDate(), data.getExpDate());
                })
                .verifyComplete();
    }

    @Test
    public void findCartDataByCartNumberTest() {
        CartData cartData = createCartData3();
        Mono<CartData> found = cartDataRepository.save(cartData)
                        .flatMap(saved -> cartDataRepository.findByCartNumber(saved.getCartNumber()));

        StepVerifier.create(found)
                .assertNext(data -> {
                    assertNotNull(data.getId());
                    assertEquals(cartData.getCartNumber(), data.getCartNumber());
                    assertEquals(cartData.getCvv(), data.getCvv());
                    assertEquals(cartData.getExpDate(), data.getExpDate());
                })
                .verifyComplete();
    }

    @Test
    public void deleteCartDataTest() {
        CartData cartData = createCartData1();
        Mono<Void> deleted = cartDataRepository.save(cartData)
                .flatMap(cartDataRepository::delete);
        StepVerifier.create(deleted)
                .verifyComplete();
    }

    @Test
    public void updateCartDataTest() {
        CartData cartData = createCartData1();
        Mono<CartData> updated = cartDataRepository.save(cartData)
                .flatMap(saved -> {
                    saved.setCartNumber("0987654321");
                    return cartDataRepository.save(saved);
                });
        StepVerifier.create(updated)
                .assertNext(data -> {
                    assertNotNull(data.getId());
                    assertEquals("0987654321", data.getCartNumber());
                    assertEquals(cartData.getCvv(), data.getCvv());
                    assertEquals(cartData.getExpDate(), data.getExpDate());
                })
                .verifyComplete();
    }

    @Test
    public void findAllCartDataTest() {

        List<CartData> cartDataList = createCartDataList();
        Flux<CartData> saved = cartDataRepository.saveAll(cartDataList)
                .thenMany(cartDataRepository.findAll());

        StepVerifier.create(saved)
                .expectNextCount(cartDataList.size())
                .assertNext(data -> {
                    assertNotNull(data.getId());
                    assertEquals(cartDataList.getLast().getCartNumber(), data.getCartNumber());
                    assertEquals(cartDataList.getLast().getCvv(), data.getCvv());
                    assertEquals(cartDataList.getLast().getExpDate(), data.getExpDate());
                })
                .expectComplete()
                .verify();
    }

    private CartData createCartData1() {
        return CartData.builder()
                .cartNumber("1234567890")
                .cvv("123")
                .expDate("12/23")
                .build();
    }

    private CartData createCartData2() {
        return CartData.builder()
                .cartNumber("0987654321")
                .cvv("321")
                .expDate("12/25")
                .build();
    }

    private CartData createCartData3() {
        return CartData.builder()
                .cartNumber("2345678901")
                .cvv("234")
                .expDate("12/25")
                .build();
    }
    private List<CartData> createCartDataList() {
        return List.of(createCartData1(), createCartData2());
    }
}