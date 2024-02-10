package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.WalletDto;
import com.dosmakhambetbaktiyar.module41.model.Wallet;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletDto toDto(Wallet wallet);
    @InheritInverseConfiguration
    Wallet toEntity(WalletDto walletDto);
}
