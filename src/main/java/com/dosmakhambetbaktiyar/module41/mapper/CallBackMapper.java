package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.CallBackDto;
import com.dosmakhambetbaktiyar.module41.model.CallBack;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallBackMapper {

    public CallBackDto toDto(CallBack entity);
    @InheritInverseConfiguration
    public CallBack toEntity(CallBackDto dto);
}
