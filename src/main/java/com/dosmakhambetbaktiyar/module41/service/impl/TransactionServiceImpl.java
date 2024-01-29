package com.dosmakhambetbaktiyar.module41.service.impl;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import com.dosmakhambetbaktiyar.module41.repository.TransactionRepository;
import com.dosmakhambetbaktiyar.module41.service.CartDataService;
import com.dosmakhambetbaktiyar.module41.service.CustomerService;
import com.dosmakhambetbaktiyar.module41.service.TransactionService;
import io.r2dbc.spi.Parameters;
import io.r2dbc.spi.R2dbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

//TODO: If okay, write logic in repositrory
//TODO: rewrite PayoutService
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private DatabaseClient client;

    @Autowired
    private CartDataService cartDataService;

    @Autowired
    private CustomerService customerService;

    @Override
    public Mono<Transaction> save(Transaction transaction) {

        System.out.println("Service : " + transaction);
        Mono<Transaction> transactionMono = client.sql("INSERT INTO transaction (" +
                        "payment_method, amount, currency, created_at, updated_at, language, notification_url, status, message, customer_id, cart_data_id) " +
                        " VALUES (:payment_method, :amount, :currency, :created_at, :updated_at, :language, :notification_url, :status, :message, :customer_id, :cart_data_id)")
                .bind("payment_method", transaction.getPaymentMethod().name())
                .bind("amount", transaction.getAmount())
                .bind("currency", transaction.getCurrency().name())
                .bind("created_at", transaction.getCreatedAt())
                .bind("updated_at", transaction.getUpdatedAt())
                .bind("language", transaction.getLanguage().name())
                .bind("notification_url", transaction.getNotificationUrl())
                .bind("status", transaction.getStatus().name())
                .bind("message", transaction.getMessage())
                .bind("customer_id", transaction.getCustomer().getId())
                .bind("cart_data_id", transaction.getCartData().getId())
                .fetch()
                .first()
                .doOnNext(map -> {
                    transaction.setTransactionId(UUID.fromString(map.get("transaction_id").toString()));
                    transaction.setCartData(cartDataService.findById(Long.parseLong(map.get("cart_data_id").toString())).block());
                    transaction.setCustomer(customerService.findById(Long.parseLong(map.get("customer_id").toString())).block());
                }).thenReturn(transaction)
                .onErrorMap(e -> new RuntimeException("Error occurred while saving transaction " + e.getMessage()));

        return transactionMono;
    }

    @Override
    public Mono<Transaction> findById(UUID uuid) {
        Mono<Transaction> transactionMono = client.sql(
                        "SELECT * FROM transaction WHERE transaction_id = ?")
                .bind(0, uuid)
                .fetch()
                .first()
                .flatMap(map -> {
                    Transaction transaction = Transaction.builder()
                            .transactionId((UUID) map.get("transaction_id"))
                            .paymentMethod((PaymentMethod) map.get("payment_method"))
                            .amount((Double) map.get("amount"))
                            .currency((Currency) map.get("currency"))
                            .createdAt((LocalDate) map.get("created_at"))
                            .updatedAt((LocalDate) map.get("updated_at"))
                            .language((Language) map.get("language"))
                            .notificationUrl((String) map.get("notification_url"))
                            .status((TransactionStatus) map.get("status"))
                            .message((String) map.get("message"))
                            .cartData(cartDataService.findById((Long) map.get("cart_data_id")).block())
                            .customer(customerService.findById((Long) map.get("customer_id")).block())
                            .build();
                    return Mono.just(transaction);
                });

        return transactionMono;
    }

    // TODO : join ???
    @Override
    public Flux<Transaction> findAll() {

        Flux<Transaction> transactionFlux = client.sql(
                        "SELECT * FROM transaction")
                .fetch()
                .all()
                .map(map -> {
                    Transaction transaction = Transaction.builder()
                            .transactionId((UUID) map.get("transaction_id"))
                            .paymentMethod( PaymentMethod.valueOf(map.get("payment_method").toString()))
                            .amount((Double) map.get("amount"))
                            .currency( Currency.valueOf(map.get("currency").toString()))
                            .createdAt(LocalDate.parse(map.get("created_at").toString().substring(0,10)))
                            .updatedAt(LocalDate.parse(map.get("updated_at").toString().substring(0,10)))
                            .language(Language.valueOf(map.get("language").toString()))
                            .notificationUrl((String) map.get("notification_url"))
                            .status(TransactionStatus.valueOf(map.get("status").toString()))
                            .message((String) map.get("message"))
                            .build();
                    return transaction;
                }).flatMap(Mono::just);

        return transactionFlux;
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {

        Mono<Transaction> transactionMono = client.sql("UPDATE transaction SET " +
                        "payment_method = ?, amount = ?, currency = ?, created_at = ?, updated_at = ?, language = ?, notification_url = ?, status = ?, message = ?, customer_id = ?, cart_data_id = ? " +
                        "WHERE transaction_id = ?")
                .bind(0, transaction.getPaymentMethod())
                .bind(1, transaction.getAmount())
                .bind(2, transaction.getCurrency())
                .bind(3, transaction.getCreatedAt())
                .bind(4, transaction.getUpdatedAt())
                .bind(5, transaction.getLanguage())
                .bind(6, transaction.getNotificationUrl())
                .bind(7, transaction.getStatus())
                .bind(8, transaction.getMessage())
                .bind(9, transaction.getCustomer().getId())
                .bind(10, transaction.getCartData().getId())
                .bind(11, transaction.getTransactionId())
                .fetch()
                .first().doOnNext(map -> {
                    transaction.setTransactionId((UUID) map.get("transaction_id"));
                    transaction.setCartData(cartDataService.findById((Long) map.get("cart_data_id")).block());
                    transaction.setCustomer(customerService.findById((Long) map.get("customer_id")).block());
                }).thenReturn(transaction);

        return repository.save(transaction);
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return repository.deleteById(uuid);
    }

    @Override
    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }
}
