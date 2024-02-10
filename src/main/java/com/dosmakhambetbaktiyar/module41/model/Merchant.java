package com.dosmakhambetbaktiyar.module41.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("merchant")
public class Merchant {
    @Id
    private String merchantId;
    private String secretKey;
}
