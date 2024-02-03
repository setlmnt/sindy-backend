package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.TokenDto;
import com.ifba.educampo.domain.dto.user.UserLoginDto;

public interface AuthService {
    TokenDto login(UserLoginDto userLoginDTO);

    String verifyToken(String token);
}
