package com.guessmewhat.backend.domain.user.exception.error;

import com.guessmewhat.backend.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserError implements ErrorProperty {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found"),
    PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "wrong password"),
    USER_EXIST(HttpStatus.CONFLICT, "user already exists"),;

    private final HttpStatus status;
    private final String message;

}
