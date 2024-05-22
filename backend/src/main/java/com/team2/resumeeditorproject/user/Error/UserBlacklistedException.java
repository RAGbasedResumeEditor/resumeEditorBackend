package com.team2.resumeeditorproject.user.Error;

import org.springframework.security.core.AuthenticationException;

public class UserBlacklistedException extends AuthenticationException {
    public UserBlacklistedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserBlacklistedException(String msg) {
        super(msg);
    }
}
