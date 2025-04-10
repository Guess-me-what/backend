package com.guessmewhat.backend.global.security.jwt.exception;

import com.guessmewhat.backend.global.exception.BusinessException;
import com.guessmewhat.backend.global.security.jwt.exception.error.JwtTokenError;

public class TokenTypeException extends BusinessException {

    public static final TokenTypeException EXCEPTION = new TokenTypeException();

    private TokenTypeException() {
        super(JwtTokenError.JWT_TOKEN_ERROR);
    }

}
