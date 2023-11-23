package com.Tasko.Registration.error;


@SuppressWarnings("serial")
public class EnterValidAmount extends Exception
{
    public EnterValidAmount() {
        super();
    }

    public EnterValidAmount(String message) {
        super(message);
    }

    public EnterValidAmount(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterValidAmount(Throwable cause) {
        super(cause);
    }

    protected EnterValidAmount(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
