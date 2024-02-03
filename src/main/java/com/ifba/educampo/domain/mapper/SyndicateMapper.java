package com.ifba.educampo.domain.mapper;

import com.ifba.educampo.domain.dto.syndicate.SyndicatePostDto;
import com.ifba.educampo.domain.dto.syndicate.SyndicatePutDto;
import com.ifba.educampo.domain.dto.syndicate.SyndicateResponseDto;
import com.ifba.educampo.domain.entity.Syndicate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SyndicateMapper {
    SyndicateResponseDto toResponseDto(Syndicate syndicate);

    @Mapping(target = "id", ignore = true)
    Syndicate responseDtoToEntity(SyndicateResponseDto syndicateResponseDto);

    SyndicatePostDto toPostDto(Syndicate syndicate);

    SyndicatePostDto toPostDto(SyndicatePutDto syndicatePutDto);

    @Mapping(target = "id", ignore = true)
    Syndicate postDtoToEntity(SyndicatePostDto syndicatePostDto);

    SyndicatePutDto toPutDto(Syndicate syndicate);

    @Mapping(target = "id", ignore = true)
    Syndicate putDtoToEntity(SyndicatePutDto syndicatePutDto);
}
