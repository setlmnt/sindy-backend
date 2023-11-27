package com.ifba.educampo.service.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.TokenDto;
import com.ifba.educampo.dto.user.UserLoginDto;
import com.ifba.educampo.exception.ForbiddenException;
import com.ifba.educampo.exception.UnauthorizedException;
import com.ifba.educampo.model.entity.user.User;
import com.ifba.educampo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
@Log
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Value("${api.security.token.jwt.secret}")
    private String secret;

    @Value("${api.security.provider}")
    private String provider;

    public TokenDto login(UserLoginDto userLoginDTO) {
        log.info("Authenticating user {}", userLoginDTO.username());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.username(),
                userLoginDTO.password()
        );

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (!authentication.isAuthenticated()) {
                throw new UnauthorizedException("Invalid credentials");
            }

            String tokenJwt = generateToken((User) authentication.getPrincipal());
            return new TokenDto(tokenJwt);
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Invalid credentials");
        }
    }

    public String verifyToken(String token) {
        log.info("Verifying token {}", token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(provider)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    private String generateToken(User user) {
        log.info("Generating token for user {}", user.getUsername());
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(provider)
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating token", exception);
        }
    }

    private Instant generateExpiresAt() {
        log.info("Generating expiration date");
        return LocalDateTime.now().plusMonths(1).toInstant(ZoneOffset.UTC);
    }
}
