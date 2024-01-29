package com.dosmakhambetbaktiyar.module41.model;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.enums.Language;
import com.dosmakhambetbaktiyar.module41.enums.PaymentMethod;
import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("payout")
public class Payout implements Serializable {
    @Id
    private UUID paymentId;
    private PaymentMethod paymentMethod;
    private Double amount;
    private Currency currency;
    private Language language;
    private String notificationUrl;
    private PayoutStatus status;
    private String message;
    @Transient
    private CartData cartData;
    @Transient
    private Customer customer;


}
