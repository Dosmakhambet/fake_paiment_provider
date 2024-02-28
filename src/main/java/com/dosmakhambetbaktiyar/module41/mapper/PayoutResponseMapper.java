package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.PayoutResponseDto;
import com.dosmakhambetbaktiyar.module41.model.Payout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayoutResponseMapper {
    PayoutResponseDto toResponseDto(Payout payout);

}
