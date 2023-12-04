package com.ifba.educampo.controller;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.user.UserPutDto;
import com.ifba.educampo.dto.user.UserResponseDto;
import com.ifba.educampo.dto.user.UserUpdatePasswordDto;
import com.ifba.educampo.service.user.OtpService;
import com.ifba.educampo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Users", description = "Users API")
@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@Log
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OtpService otpService;

    @Operation(summary = "Find all users")
    @GetMapping
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @Operation(summary = "Find user by id")
    @GetMapping("/me")
    public UserResponseDto findById(Principal principal) {
        return userService.findByName(principal.getName());
    }

    @Operation(summary = "Update user")
    @PutMapping
    public UserResponseDto update(Principal principal, @RequestBody UserPutDto userPutDto) {
        return userService.updateByName(principal.getName(), userPutDto);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping
    public void delete(Principal principal) {
        userService.deleteByName(principal.getName());
    }

    @Operation(summary = "Send otp to email")
    @GetMapping("/change-password/send-otp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendOtp(
            Principal principal
    ) {
        UserResponseDto userResponseDto = userService.findByName(principal.getName());
        String otpCode = otpService.create(userResponseDto);
        otpService.sendOtpToEmail(userResponseDto, otpCode);
    }

    @Operation(summary = "Change password")
    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestParam String otp,
            Principal principal,
            @RequestBody @Valid UserUpdatePasswordDto changePasswordDto
    ) {
        UserResponseDto userResponseDto = userService.findByName(principal.getName());
        otpService.verifyOtp(userResponseDto.username(), otp);
        userService.updatePassword(userResponseDto.id(), changePasswordDto);
    }
}
