package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.email.EmailResponseDto;
import br.com.sindy.domain.entity.email.CommunicationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mapping(target = "status", source = "status.description")
    EmailResponseDto toResponseDto(CommunicationHistory communicationHistory);
}
