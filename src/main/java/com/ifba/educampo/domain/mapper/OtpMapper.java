package com.ifba.educampo.domain.mapper;

import com.ifba.educampo.domain.dto.user.OtpDto;
import com.ifba.educampo.domain.entity.user.Otp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpMapper {
    Otp dtoToEntity(OtpDto otpDto);
}