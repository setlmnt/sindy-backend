package br.com.sindy.domain.enums.converter;

import br.com.sindy.domain.enums.EmailStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class EmailStatusEnumConverter implements AttributeConverter<EmailStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(EmailStatusEnum migrationStatus) {
        if (migrationStatus == null) {
            return null;
        }
        return migrationStatus.getValue();
    }

    @Override
    public EmailStatusEnum convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Stream.of(EmailStatusEnum.values())
                .filter((c) -> StringUtils.equalsAnyIgnoreCase(c.getValue(), value))
                .findFirst()
                .orElse(null);
    }
}