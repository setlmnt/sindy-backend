package com.ifba.educampo.mapper;

import com.ifba.educampo.dto.localOffice.LocalOfficePostDto;
import com.ifba.educampo.dto.localOffice.LocalOfficePutDto;
import com.ifba.educampo.dto.localOffice.LocalOfficeResponseDto;
import com.ifba.educampo.entity.LocalOffice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalOfficeMapper {
    LocalOfficeResponseDto toResponseDto(LocalOffice localOffice);

    @Mapping(target = "id", ignore = true)
    LocalOffice responseDtoToEntity(LocalOfficeResponseDto localOfficeResponseDto);

    @Mapping(target = "id", ignore = false)
    LocalOffice responseDtoToEntityWithId(LocalOfficeResponseDto localOfficeResponseDto);

    LocalOfficePostDto toPostDto(LocalOffice localOffice);

    @Mapping(target = "id", ignore = true)
    LocalOffice postDtoToEntity(LocalOfficePostDto localOfficePostDto);

    LocalOfficePutDto toPutDto(LocalOffice localOffice);

    @Mapping(target = "id", ignore = true)
    LocalOffice putDtoToEntity(LocalOfficePutDto localOfficePutDto);
}
