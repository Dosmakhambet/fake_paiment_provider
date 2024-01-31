package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.CartDataDto;
import com.dosmakhambetbaktiyar.module41.model.CartData;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartDataMapper {
    CartDataDto toDto(CartData cartData);

    @InheritInverseConfiguration
    CartData toEntity(CartDataDto cartDataDto);
}
