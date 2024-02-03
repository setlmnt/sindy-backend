package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.syndicate.SyndicatePostDto;
import br.com.sindy.domain.dto.syndicate.SyndicatePutDto;
import br.com.sindy.domain.dto.syndicate.SyndicateResponseDto;
import br.com.sindy.domain.entity.Syndicate;
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
