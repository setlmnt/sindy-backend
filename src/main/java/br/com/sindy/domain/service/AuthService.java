package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.TokenDto;
import br.com.sindy.domain.dto.user.UserLoginDto;

public interface AuthService {
    TokenDto login(UserLoginDto userLoginDTO);

    String verifyToken(String token);
}
