package com.dosmakhambetbaktiyar.module41.service;

import com.dosmakhambetbaktiyar.module41.model.Transaction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionService extends GenericService<Transaction, UUID> {
}
