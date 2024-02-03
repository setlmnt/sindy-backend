package br.com.sindy.domain.mapper.associate;

import br.com.sindy.domain.dto.associate.affiliation.AffiliationPostDto;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationPutDto;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationResponseDto;
import br.com.sindy.domain.entity.associate.Affiliation;
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
