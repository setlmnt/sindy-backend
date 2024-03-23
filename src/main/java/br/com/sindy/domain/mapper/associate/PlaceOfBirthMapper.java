package br.com.sindy.domain.mapper.associate;

import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthResponseDto;
import br.com.sindy.domain.entity.associate.PlaceOfBirth;
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
