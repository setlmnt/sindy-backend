package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.FileResponseDto;
import br.com.sindy.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponseDto toResponseDto(File file);

    @Mapping(target = "id", ignore = true)
    File responseDtoToEntity(FileResponseDto fileResponseDto);
}
