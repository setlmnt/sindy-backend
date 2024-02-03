package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.user.OtpDto;
import br.com.sindy.domain.entity.user.Otp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpMapper {
    Otp dtoToEntity(OtpDto otpDto);
}