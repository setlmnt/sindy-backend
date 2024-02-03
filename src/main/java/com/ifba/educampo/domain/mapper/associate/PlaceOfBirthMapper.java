package com.ifba.educampo.domain.mapper.associate;

import com.ifba.educampo.domain.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import com.ifba.educampo.domain.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import com.ifba.educampo.domain.dto.associate.placeOfBirth.PlaceOfBirthResponseDto;
import com.ifba.educampo.domain.entity.associate.PlaceOfBirth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceOfBirthMapper {
    PlaceOfBirthResponseDto toResponseDto(PlaceOfBirth placeOfBirth);

    @Mapping(target = "id", ignore = true)
    PlaceOfBirth responseDtoToEntity(PlaceOfBirthResponseDto placeOfBirthResponseDto);

    PlaceOfBirthPostDto toPostDto(PlaceOfBirth placeOfBirth);

    @Mapping(target = "id", ignore = true)
    PlaceOfBirth postDtoToEntity(PlaceOfBirthPostDto placeOfBirthPostDto);

    PlaceOfBirthPutDto toPutDto(PlaceOfBirth placeOfBirth);

    @Mapping(target = "id", ignore = true)
    PlaceOfBirth putDtoToEntity(PlaceOfBirthPutDto associatePutDto);
}
