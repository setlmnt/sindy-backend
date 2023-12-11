package com.ifba.educampo.mapper.associate;

import com.ifba.educampo.dto.associate.affiliation.AffiliationPostDto;
import com.ifba.educampo.dto.associate.affiliation.AffiliationPutDto;
import com.ifba.educampo.dto.associate.affiliation.AffiliationResponseDto;
import com.ifba.educampo.entity.associate.Affiliation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AffiliationMapper {
    AffiliationResponseDto toResponseDto(Affiliation affiliation);

    @Mapping(target = "id", ignore = true)
    Affiliation responseDtoToEntity(AffiliationResponseDto affiliationResponseDto);

    AffiliationPostDto toPostDto(Affiliation affiliation);

    @Mapping(target = "id", ignore = true)
    Affiliation postDtoToEntity(AffiliationPostDto affiliationPostDto);

    AffiliationPutDto toPutDto(Affiliation affiliation);

    @Mapping(target = "id", ignore = true)
    Affiliation putDtoToEntity(AffiliationPutDto associatePutDto);
}
