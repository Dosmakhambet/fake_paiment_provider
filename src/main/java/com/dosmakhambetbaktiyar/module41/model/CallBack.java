package com.dosmakhambetbaktiyar.module41.model;

import com.dosmakhambetbaktiyar.module41.enums.PayoutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("call_back")
public class CallBack {
    @Id
    private Long id;
    private PayoutStatus status;
    private LocalDateTime createdAt;
    private UUID payoutId;
    @Transient
    private Payout payout;
}
