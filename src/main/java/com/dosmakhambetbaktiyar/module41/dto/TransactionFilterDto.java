package com.dosmakhambetbaktiyar.module41.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TransactionFilterDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TransactionFilterDto() {
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now();
    }
}
