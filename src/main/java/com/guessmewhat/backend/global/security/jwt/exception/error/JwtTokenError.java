package com.guessmewhat.backend.global.security.jwt.exception.error;

import com.guessmewhat.backend.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtTokenError implements ErrorProperty {

    JWT_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "wrong type");

    private final HttpStatus status;
    private final String message;

}
