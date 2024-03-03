package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.monthlyFee.MonthlyFeePostDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeePutDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeeResponseDto;
import br.com.sindy.domain.entity.MonthlyFee;
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
