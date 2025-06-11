package com.guessmewhat.backend.domain.auth.service;

import com.guessmewhat.backend.domain.auth.client.request.SignInRequest;
import com.guessmewhat.backend.domain.auth.client.request.SignUpRequest;
import com.guessmewhat.backend.domain.auth.service.response.JsonWebTokenResponse;
import com.guessmewhat.backend.domain.auth.service.response.RefreshTokenResponse;
import com.guessmewhat.backend.domain.user.domain.entity.UserEntity;
import com.guessmewhat.backend.domain.user.domain.enums.UserRole;
import com.guessmewhat.backend.domain.user.domain.repository.jpa.UserRepository;
import com.guessmewhat.backend.domain.user.exception.PasswordWrongException;
import com.guessmewhat.backend.domain.user.exception.UserExistException;
import com.guessmewhat.backend.domain.user.exception.UserNotFoundException;
import com.guessmewhat.backend.global.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public void signUp(SignUpRequest request) {
        if (checkUserByEmail(request.email())){
            throw UserExistException.EXCEPTION;
        }
        userRepository.save(UserEntity.builder()
                .email(request.email())
                .nickname(request.nickname())
                .password(encoder.encode(request.password()))
                .userRole(UserRole.USER)
                .build()
        );
    }

    public JsonWebTokenResponse signIn(SignInRequest request) {
        if(!checkUserByEmail(request.email())){
            throw UserNotFoundException.EXCEPTION;
        }

        String userPassword = userRepository.getByEmail(request.email()).getPassword();
        UserEntity user = userRepository.getByEmail(request.email());

        if (!encoder.matches(request.password(), userPassword))
            throw PasswordWrongException.EXCEPTION;

        return JsonWebTokenResponse.builder()
                .nickname(user.getNickname())
                .accessToken(jwtProvider.generateAccessToken(request.email(), UserRole.USER))
                .refreshToken(jwtProvider.generateRefreshToken(request.email(), UserRole.USER))
                .build();
    }

    public RefreshTokenResponse refresh(String token) {
        Jws<Claims> claims = jwtProvider.getClaims(token);
        return RefreshTokenResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(claims.getBody().getSubject(),
                        (UserRole) claims.getHeader().get("authority"))).build();
    }

    public boolean checkUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
