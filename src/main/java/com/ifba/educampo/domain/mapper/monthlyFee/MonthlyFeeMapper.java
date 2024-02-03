package com.ifba.educampo.domain.mapper.monthlyFee;

import com.ifba.educampo.domain.dto.monthlyFee.MonthlyFeePostDto;
import com.ifba.educampo.domain.dto.monthlyFee.MonthlyFeePutDto;
import com.ifba.educampo.domain.dto.monthlyFee.MonthlyFeeResponseDto;
import com.ifba.educampo.domain.entity.MonthlyFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonthlyFeeMapper {
    MonthlyFeeResponseDto toResponseDto(MonthlyFee monthlyFee);

    @Mapping(target = "id", ignore = true)
    MonthlyFee responseDtoToEntity(MonthlyFeeResponseDto monthlyFeeResponseDto);

    MonthlyFeePostDto toPostDto(MonthlyFee monthlyFee);

    @Mapping(target = "id", ignore = true)
    MonthlyFee postDtoToEntity(MonthlyFeePostDto monthlyFeePostDto);

    MonthlyFeePutDto toPutDto(MonthlyFee monthlyFee);

    @Mapping(target = "id", ignore = true)
    MonthlyFee putDtoToEntity(MonthlyFeePutDto monthlyFeePutDto);
}
