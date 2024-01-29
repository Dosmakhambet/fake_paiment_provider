package com.dosmakhambetbaktiyar.module41.dto;

import com.dosmakhambetbaktiyar.module41.model.CartData;
import lombok.*;

import java.io.Serializable;
//TODO: mapstuct

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

    public static CartDataDto toDto(CartData entity){
        return CartDataDto.builder()
                .id(entity.getId())
                .cartNumber(entity.getCartNumber())
                .expDate(entity.getExpDate())
                .cvv(entity.getCvv())
                .build();
    }

    public CartData toEntity(){
        return CartData.builder()
                .id(this.id)
                .cartNumber(this.cartNumber)
                .expDate(this.expDate)
                .cvv(this.cvv)
                .build();
    }
}
