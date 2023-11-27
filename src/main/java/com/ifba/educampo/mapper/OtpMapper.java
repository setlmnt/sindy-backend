package com.ifba.educampo.mapper;

import com.ifba.educampo.dto.user.OtpDto;
import com.ifba.educampo.model.entity.user.Otp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpMapper {
    Otp dtoToEntity(OtpDto otpDto);
}