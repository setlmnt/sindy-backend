package com.ifba.educampo.dto.syndicate;

import com.ifba.educampo.dto.address.AddressPutDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SyndicatePutDto(
        String name, // Nome

        @CNPJ(message = "CNPJ must be in the format xx.xxx.xxx/xxxx-xx")
        String cnpj, // CNPJ

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate foundationDate, // Data de Fundação

        @Email
        String email, // E-mail

        @Size(min = 12, max = 13, message = "Phone must be 12 or 13 characters long")
        @Pattern(regexp = "^(?:\\d{2} \\d{5}-\\d{4}|\\d{2} \\d{4}-\\d{4})$", message = "Phone must be in the format xx xxxx-xxxx or xx xxxxx-xxxx")
        String phone, // Telefone

        @Valid
        AddressPutDto address // Endereço
) {
}
