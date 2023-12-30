package com.ifba.educampo.service;

import com.ifba.educampo.dto.TokenDto;
import com.ifba.educampo.dto.user.UserLoginDto;

public interface AuthService {
    TokenDto login(UserLoginDto userLoginDTO);

    String verifyToken(String token);
}
