package com.ifba.educampo.domain.mapper;

import com.ifba.educampo.domain.dto.FileResponseDto;
import com.ifba.educampo.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponseDto toResponseDto(File file);

    @Mapping(target = "id", ignore = true)
    File responseDtoToEntity(FileResponseDto fileResponseDto);
}
