package com.guessmewhat.backend.domain.user.exception;

import com.guessmewhat.backend.domain.user.exception.error.UserError;
import com.guessmewhat.backend.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public static final UserNotFoundException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException(){
        super(UserError.USER_NOT_FOUND);
    }

}
