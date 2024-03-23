package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.FileResponseDto;
import br.com.sindy.domain.entity.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponseDto toResponseDto(File file);

    File responseDtoToEntity(FileResponseDto dto);
}
