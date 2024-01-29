package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.TransactionStatus;
import com.dosmakhambetbaktiyar.module41.model.CartData;
import com.dosmakhambetbaktiyar.module41.model.Customer;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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

    public static TransactionDto toDto(Transaction entity){
        return TransactionDto.builder()
                .transactionId(entity.getTransactionId())
                .paymentMethod(entity.getPaymentMethod())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .language(entity.getLanguage())
                .notificationUrl(entity.getNotificationUrl())
                .status(entity.getStatus())
                .message(entity.getMessage())
               // .customer(CustomerDto.toDto(entity.getCustomer()))
               // .cartData(CartDataDto.toDto(entity.getCartData()))
                .build();
    }

    public Transaction toEntity(){
        return Transaction.builder()
                .transactionId(this.transactionId)
                .paymentMethod(this.paymentMethod)
                .amount(this.amount)
                .currency(this.currency)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .language(this.language)
                .notificationUrl(this.notificationUrl)
                .status(this.status)
                .message(this.message)
             //   .customer(this.customer.toEntity())
             //   .cartData(this.cartData.toEntity())
                .build();
    }
}
