package com.guessmewhat.backend.domain.user.exception;

import com.guessmewhat.backend.domain.user.exception.error.UserError;
import com.guessmewhat.backend.global.exception.BusinessException;

public class PasswordWrongException extends BusinessException {

    public static final PasswordWrongException EXCEPTION = new PasswordWrongException();

    private PasswordWrongException(){
        super(UserError.PASSWORD_WRONG);
    }

}
