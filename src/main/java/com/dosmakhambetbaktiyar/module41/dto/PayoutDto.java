package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import com.dosmakhambetbaktiyar.module41.model.CartData;
import com.dosmakhambetbaktiyar.module41.model.Customer;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
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
}
