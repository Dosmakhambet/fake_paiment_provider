package com.dosmakhambetbaktiyar.module41.model;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("wallet")
public class Wallet {
    @Id
    private Long id;
    private Currency currency;
    private Double balance;
    private String merchantId;
    @Transient
    private Merchant merchant;
    @Transient
    private List<Transaction> transactions;
    @Transient
    private List<Payout> payouts;
}
