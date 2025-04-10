package com.guessmewhat.backend.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorProperty {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "an error occurred on the server"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "bas request"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "resource not found"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "you do not have permission");

    private final HttpStatus status;
    private final String message;

}
