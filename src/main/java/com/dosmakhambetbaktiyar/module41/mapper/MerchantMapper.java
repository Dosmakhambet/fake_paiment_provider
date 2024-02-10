package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.MerchantDto;
import com.dosmakhambetbaktiyar.module41.model.Merchant;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    MerchantDto toDto(Merchant merchant);
    @InheritInverseConfiguration
    Merchant toEntity(MerchantDto merchantDto);

}
