package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.UserDto;
import com.dosmakhambetbaktiyar.module41.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);
}
