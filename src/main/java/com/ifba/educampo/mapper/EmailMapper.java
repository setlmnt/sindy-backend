package com.ifba.educampo.mapper;

import com.ifba.educampo.dto.email.EmailDto;
import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mapping(target = "id", ignore = true)
    Email dtoToEntity(EmailDto emailDto);

    EmailResponseDto toResponseDto(Email email);
}
