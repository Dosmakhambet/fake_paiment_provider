package com.dosmakhambetbaktiyar.module41.mapper;

import com.dosmakhambetbaktiyar.module41.dto.TransactionDto;
import com.dosmakhambetbaktiyar.module41.dto.TransactionResponseDto;
import com.dosmakhambetbaktiyar.module41.model.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto toDto(Transaction transaction);
    @InheritInverseConfiguration
    Transaction toEntity(TransactionDto transactionDto);
    TransactionResponseDto toResponseDto(Transaction transaction);
}
