package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.PayoutDto;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayoutMapper {
    PayoutDto toDto(Payout payout);
    @InheritInverseConfiguration
    Payout toEntity(PayoutDto payoutDto);
}
