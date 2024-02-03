package com.ifba.educampo.domain.dto.syndicate;

import com.ifba.educampo.domain.dto.address.AddressPostDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SyndicatePostDto(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name, // Nome

        @NotNull(message = "CNPJ is required")
        @CNPJ(message = "CNPJ must be in the format xx.xxx.xxx/xxxx-xx")
        String cnpj, // CNPJ

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate foundationDate, // Data de Fundação

        @NotNull(message = "Email is required")
        @Email
        String email, // E-mail

        @NotNull(message = "Phone is required")
        @Size(min = 12, max = 13, message = "Phone must be 12 or 13 characters long")
        @Pattern(regexp = "^(?:\\d{2} \\d{5}-\\d{4}|\\d{2} \\d{4}-\\d{4})$", message = "Phone must be in the format xx xxxx-xxxx or xx xxxxx-xxxx")
        String phone, // Telefone

        @NotNull(message = "Address is required")
        @Valid
        AddressPostDto address // Endereço
) {
}
