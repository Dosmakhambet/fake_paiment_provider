package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.enums.Currency;
import com.dosmakhambetbaktiyar.module41.model.Merchant;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
    private Long id;
    private Currency currency;
    private Long balance;
    private String merchantId;
    @JsonIgnore
    private Merchant merchant;
    @JsonIgnore
    private List<Transaction> transactions;
    @JsonIgnore
    private List<PayoutDto> payouts;
}
