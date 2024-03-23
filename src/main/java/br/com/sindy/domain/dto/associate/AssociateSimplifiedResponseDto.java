package br.com.sindy.domain.dto.associate;

import br.com.sindy.domain.dto.FileSimplifiedResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AssociateSimplifiedResponseDto(
        Long id,
        String name,
        Long unionCard,
        String cpf,
        Boolean deleted,
        FileSimplifiedResponseDto profilePicture
) {
}
