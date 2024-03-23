package br.com.sindy.api.controller;

import br.com.sindy.domain.dto.TokenDto;
import br.com.sindy.domain.dto.user.UserLoginDto;
import br.com.sindy.domain.dto.user.UserRegisterDto;
import br.com.sindy.domain.dto.user.UserResponseDto;
import br.com.sindy.domain.service.AuthService;
import br.com.sindy.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/v1/users/auth")
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
    @SecurityRequirements({
            @SecurityRequirement(name = "bearerAuth"),
            @SecurityRequirement(name = "cookieAuth")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @RequestBody @Valid UserRegisterDto userRegisterDTO
    ) {
        return userService.save(userRegisterDTO);
    }

    @Operation(summary = "Validate token")
    @SecurityRequirements({
            @SecurityRequirement(name = "bearerAuth"),
            @SecurityRequirement(name = "cookieAuth")
    })
    @PostMapping("/validate")
    public void validate(
            @RequestBody @Valid TokenDto tokenDTO
    ) {
        authService.verifyToken(tokenDTO.token());
    }
}