package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.CartData;
import com.dosmakhambetbaktiyar.module41.model.Customer;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PayoutDto implements Serializable {
    private UUID paymentId;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Currency currency;
    private CartData cartData;
    private Language language;
    private String notificationUrl;
    private Customer customer;
    private PayoutStatus status;
    private String message;

    public static PayoutDto toDto(Payout entity){
        return PayoutDto.builder()
                .paymentId(entity.getPaymentId())
                .paymentMethod(entity.getPaymentMethod())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .cartData(entity.getCartData())
                .language(entity.getLanguage())
                .notificationUrl(entity.getNotificationUrl())
                .customer(entity.getCustomer())
                .status(entity.getStatus())
                .message(entity.getMessage())
                .build();
    }

    public Payout toEntity(){
        return Payout.builder()
                .paymentId(this.paymentId)
                .paymentMethod(this.paymentMethod)
                .amount(this.amount)
                .currency(this.currency)
                .cartData(this.cartData)
                .language(this.language)
                .notificationUrl(this.notificationUrl)
                .customer(this.customer)
                .status(this.status)
                .message(this.message)
                .build();
    }
}
