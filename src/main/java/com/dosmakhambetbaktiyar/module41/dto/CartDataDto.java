package com.dosmakhambetbaktiyar.module41.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDataDto implements Serializable {
    private Long id;
    private String cartNumber;
    private String expDate;
    private String cvv;
}
