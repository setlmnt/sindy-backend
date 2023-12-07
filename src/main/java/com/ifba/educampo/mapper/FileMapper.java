package com.ifba.educampo.mapper;

import com.ifba.educampo.dto.FileResponseDto;
import com.ifba.educampo.model.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponseDto toResponseDto(File file);

    @Mapping(target = "id", ignore = true)
    File responseDtoToEntity(FileResponseDto fileResponseDto);
}
