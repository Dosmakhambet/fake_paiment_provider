package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDto implements Serializable {
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
    private CustomerDto customer;
    private CartDataDto cartData;
    private WalletDto wallet;
}
