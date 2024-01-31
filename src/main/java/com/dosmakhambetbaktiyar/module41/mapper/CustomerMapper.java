package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.CustomerDto;
import com.dosmakhambetbaktiyar.module41.model.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

    @InheritInverseConfiguration
    Customer toEntity(CustomerDto customerDto);
}
