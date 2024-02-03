package com.ifba.educampo.domain.mapper;

import com.ifba.educampo.domain.dto.user.UserLoginDto;
import com.ifba.educampo.domain.dto.user.UserPutDto;
import com.ifba.educampo.domain.dto.user.UserRegisterDto;
import com.ifba.educampo.domain.dto.user.UserResponseDto;
import com.ifba.educampo.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDto(List<User> users);

    @Mapping(target = "id", ignore = true)
    User responseDtoToEntity(UserResponseDto userResponseDto);

    @Mapping(target = "id", ignore = true)
    User registerDtoToEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "id", ignore = true)
    User loginDtoToEntity(UserLoginDto userLoginDto);

    User putDtoToEntity(UserPutDto userPutDto);
}