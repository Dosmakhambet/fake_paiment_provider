package com.dosmakhambetbaktiyar.module41.dto;


import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDto {
    private String merchantId;
    private String secretKey;
}
