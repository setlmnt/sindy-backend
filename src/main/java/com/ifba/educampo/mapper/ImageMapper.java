package com.ifba.educampo.mapper;

import com.ifba.educampo.dto.ImageResponseDto;
import com.ifba.educampo.model.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponseDto toResponseDto(Image image);

    @Mapping(target = "id", ignore = true)
    Image responseDtoToEntity(ImageResponseDto imageResponseDto);
}
