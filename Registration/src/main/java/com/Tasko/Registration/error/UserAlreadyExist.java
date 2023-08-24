package com.Tasko.Registration.error;

public class UserAlreadyExist extends Exception
{
    public UserAlreadyExist() {
        super();
    }

    public UserAlreadyExist(String message) {
        super(message);
    }

    public UserAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExist(Throwable cause) {
        super(cause);
    }

    protected UserAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
