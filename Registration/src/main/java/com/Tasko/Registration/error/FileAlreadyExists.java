package com.Tasko.Registration.error;

public class FileAlreadyExists extends Exception
{
    public FileAlreadyExists() {
        super();
    }

    public FileAlreadyExists(String message) {
        super(message);
    }

    public FileAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public FileAlreadyExists(Throwable cause) {
        super(cause);
    }

    protected FileAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
