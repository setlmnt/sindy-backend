package br.com.sindy.domain.mapper.associate;

import br.com.sindy.domain.dto.associate.dependents.DependentsPostDto;
import br.com.sindy.domain.dto.associate.dependents.DependentsPutDto;
import br.com.sindy.domain.dto.associate.dependents.DependentsResponseDto;
import br.com.sindy.domain.entity.associate.Dependents;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DependentsMapper {
    DependentsResponseDto toResponseDto(Dependents dependents);

    @Mapping(target = "id", ignore = true)
    Dependents responseDtoToEntity(DependentsResponseDto dependentsResponseDto);

    DependentsPostDto toPostDto(Dependents dependents);

    DependentsPostDto toPostDto(DependentsPutDto dependentsPutDto);

    @Mapping(target = "id", ignore = true)
    Dependents postDtoToEntity(DependentsPostDto dependentsPostDto);

    DependentsPutDto toPutDto(Dependents dependents);

    @Mapping(target = "id", ignore = true)
    Dependents putDtoToEntity(DependentsPutDto associatePutDto);
}
