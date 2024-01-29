package com.dosmakhambetbaktiyar.module41.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("cart_data")
public class CartData implements Serializable {
    @Id
    private Long id;
    private String cartNumber;
    private String expDate;
    private String cvv;
}
