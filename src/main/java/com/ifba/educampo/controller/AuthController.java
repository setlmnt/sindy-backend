package com.ifba.educampo.controller;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.TokenDto;
import com.ifba.educampo.dto.user.UserLoginDto;
import com.ifba.educampo.dto.user.UserRegisterDto;
import com.ifba.educampo.dto.user.UserResponseDto;
import com.ifba.educampo.service.user.AuthService;
import com.ifba.educampo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/v1/users/auth")
@Log
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public TokenDto login(
            @RequestBody @Valid UserLoginDto userLoginDTO
    ) {

        return authService.login(userLoginDTO);
    }

    @Operation(summary = "Register user")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @RequestBody @Valid UserRegisterDto userRegisterDTO
    ) {
        return userService.save(userRegisterDTO);
    }

    @Operation(summary = "Validate token")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/validate")
    public void validate(
            @RequestBody @Valid TokenDto tokenDTO
    ) {
        authService.verifyToken(tokenDTO.token());
    }
}