package com.ifba.educampo.domain.mapper.associate;

import com.ifba.educampo.domain.dto.associate.AssociatePostDto;
import com.ifba.educampo.domain.dto.associate.AssociatePutDto;
import com.ifba.educampo.domain.dto.associate.AssociateResponseDto;
import com.ifba.educampo.domain.entity.associate.Associate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssociateMapper {
    AssociateResponseDto toResponseDto(Associate associate);

    @Mapping(target = "id", ignore = true)
    Associate responseDtoToEntity(AssociateResponseDto associateResponseDto);

    @Mapping(target = "id")
    Associate responseDtoToEntityWithId(AssociateResponseDto associateResponseDto);

    AssociatePostDto toPostDto(Associate associate);

    @Mapping(target = "id", ignore = true)
    Associate postDtoToEntity(AssociatePostDto associatePostDto);

    AssociatePutDto toPutDto(Associate associate);

    @Mapping(target = "id", ignore = true)
    Associate putDtoToEntity(AssociatePutDto associatePutDto);


}
