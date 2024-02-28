package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PayoutDto implements Serializable {
    private UUID payoutId;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Currency currency;
    private Language language;
    private String notificationUrl;
    private PayoutStatus status;
    private String message;
    private CustomerDto customer;
    private CartDataDto cartData;
    private WalletDto wallet;
    private List<CallBackDto> callbacks;

}
