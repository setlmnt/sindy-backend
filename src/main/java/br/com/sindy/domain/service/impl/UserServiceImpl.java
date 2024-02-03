package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.user.UserPutDto;
import br.com.sindy.domain.dto.user.UserRegisterDto;
import br.com.sindy.domain.dto.user.UserResponseDto;
import br.com.sindy.domain.dto.user.UserUpdatePasswordDto;
import br.com.sindy.domain.entity.user.User;
import br.com.sindy.domain.enums.ErrorsEnum;
import br.com.sindy.domain.enums.RoleEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.mapper.UserMapper;
import br.com.sindy.domain.repository.UserRepository;
import br.com.sindy.domain.service.UserService;
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
public class UserServiceImpl implements UserService {
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
            throw new ApiException(ErrorsEnum.INVALID_USERNAME);
        }

        user = userMapper.registerDtoToEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        user.setRole(RoleEnum.USER);

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
