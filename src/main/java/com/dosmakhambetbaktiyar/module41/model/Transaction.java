package com.dosmakhambetbaktiyar.module41.model;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction")
public class Transaction implements Serializable {
    @Id
    private UUID transactionId;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Currency currency;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Language language;
    private String notificationUrl;
    private TransactionStatus status;
    private String message;
    @Transient
    private Customer customer;
    @Transient
    private CartData cartData;
}
