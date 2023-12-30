package com.ifba.educampo.service.user;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.user.UserPutDto;
import com.ifba.educampo.dto.user.UserRegisterDto;
import com.ifba.educampo.dto.user.UserResponseDto;
import com.ifba.educampo.dto.user.UserUpdatePasswordDto;
import com.ifba.educampo.entity.user.Role;
import com.ifba.educampo.entity.user.User;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.mapper.UserMapper;
import com.ifba.educampo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Page<UserResponseDto> findAll(Pageable pageable) {
        log.info("Finding all users");
        return userRepository.findAll(pageable).map(userMapper::toResponseDto);
    }

    public UserResponseDto findByName(String name) {
        log.info("Finding user by name {}", name);
        User user = userRepository.findUserByUsernameAndDeletedFalse(name);
        return userMapper.toResponseDto(user);
    }

    public UserResponseDto save(UserRegisterDto userRegisterDto) {
        log.info("Saving user {}", userRegisterDto.username());
        User user = userRepository.findUserByUsername(userRegisterDto.username());

        if (user != null) {
            throw new BadRequestException("Invalid username");
        }

        user = userMapper.registerDtoToEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        user.setRole(Role.USER);

        return userMapper.toResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateByName(String name, UserPutDto userPutDto) {
        log.info("Updating user {}", name);
        User user = userRepository.getReferenceById(findByName(name).id());

        user.update(userMapper.putDtoToEntity(userPutDto));
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public void updatePassword(Long id, UserUpdatePasswordDto changePasswordDto) {
        log.info("Updating password for user {}", id);
        User user = userRepository.getReferenceById(id);

        user.setPassword(passwordEncoder.encode(changePasswordDto.password()));
    }

    public void deleteByName(String name) {
        log.info("Deleting user {}", name);
        User user = userRepository.getReferenceById(findByName(name).id());
        user.delete();
    }
}
