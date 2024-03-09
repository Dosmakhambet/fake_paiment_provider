package com.dosmakhambetbaktiyar.module41.model;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("payout")
public class Payout implements Serializable {
    @Id
    private UUID payoutId;
    private Double amount;
    private Currency currency;
    private Language language;
    private String notificationUrl;
    private PayoutStatus status;
    private String message;
    private Long customerId;
    private Long cartDataId;
    private Long walletId;
    @Transient
    private CartData cartData;
    @Transient
    private Customer customer;
    @Transient
    private Wallet wallet;
    @Transient
    private List<CallBack> callBacks;
}
