package com.ifba.educampo.service.impl;

import com.ifba.educampo.dto.user.UserPutDto;
import com.ifba.educampo.dto.user.UserRegisterDto;
import com.ifba.educampo.dto.user.UserResponseDto;
import com.ifba.educampo.dto.user.UserUpdatePasswordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponseDto> findAll(Pageable pageable);

    UserResponseDto findByName(String name);

    UserResponseDto save(UserRegisterDto userRegisterDto);

    UserResponseDto updateByName(String name, UserPutDto userPutDto);

    void deleteByName(String name);

    void updatePassword(Long id, UserUpdatePasswordDto changePasswordDto);
}
