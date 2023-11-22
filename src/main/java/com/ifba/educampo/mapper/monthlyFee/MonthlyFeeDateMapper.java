package com.ifba.educampo.mapper.monthlyFee;

import com.ifba.educampo.dto.monthlyFee.date.MonthlyFeeDateDto;
import com.ifba.educampo.dto.monthlyFee.date.MonthlyFeeDateResponseDto;
import com.ifba.educampo.model.entity.MonthlyFeeDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonthlyFeeDateMapper {
    MonthlyFeeDateResponseDto toResponseDto(MonthlyFeeDate monthlyFeeDate);

    @Mapping(target = "id", ignore = true)
    MonthlyFeeDate responseDtoToEntity(MonthlyFeeDateResponseDto monthlyFeeDateResponseDto);

    MonthlyFeeDateDto toDto(MonthlyFeeDate monthlyFeeDate);

    @Mapping(target = "id", ignore = true)
    MonthlyFeeDate dtoToEntity(MonthlyFeeDateDto monthlyFeeDateDto);
}
