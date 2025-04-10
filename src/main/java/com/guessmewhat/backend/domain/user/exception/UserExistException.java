package com.guessmewhat.backend.domain.user.exception;

import com.guessmewhat.backend.domain.user.exception.error.UserError;
import com.guessmewhat.backend.global.exception.BusinessException;

public class UserExistException extends BusinessException {

    public static final UserExistException EXCEPTION = new UserExistException();

    public UserExistException() {
        super(UserError.USER_EXIST);
    }
}
