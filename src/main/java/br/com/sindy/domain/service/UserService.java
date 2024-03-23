package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.user.UserPutDto;
import br.com.sindy.domain.dto.user.UserRegisterDto;
import br.com.sindy.domain.dto.user.UserResponseDto;
import br.com.sindy.domain.dto.user.UserUpdatePasswordDto;
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
