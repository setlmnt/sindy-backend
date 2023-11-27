package com.ifba.educampo.controller;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.user.UserPutDto;
import com.ifba.educampo.dto.user.UserResponseDto;
import com.ifba.educampo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Users", description = "Users API")
@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@Log
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
}
