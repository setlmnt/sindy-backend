package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.localOffice.LocalOfficePostDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficePutDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficeResponseDto;
import br.com.sindy.domain.entity.LocalOffice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalOfficeMapper {
    LocalOfficeResponseDto toResponseDto(LocalOffice localOffice);

    @Mapping(target = "id", ignore = true)
    LocalOffice responseDtoToEntity(LocalOfficeResponseDto localOfficeResponseDto);

    LocalOffice responseDtoToEntityWithId(LocalOfficeResponseDto localOfficeResponseDto);

    LocalOfficePostDto toPostDto(LocalOffice localOffice);

    @Mapping(target = "id", ignore = true)
    LocalOffice postDtoToEntity(LocalOfficePostDto localOfficePostDto);

    LocalOfficePutDto toPutDto(LocalOffice localOffice);

    @Mapping(target = "id", ignore = true)
    LocalOffice putDtoToEntity(LocalOfficePutDto localOfficePutDto);
}
